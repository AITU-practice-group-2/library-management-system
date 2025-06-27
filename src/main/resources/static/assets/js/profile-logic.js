window.addEventListener("DOMContentLoaded", () => {
    const userId = document.getElementById("userProfileData")?.dataset?.userId;
    const userRole = document.getElementById("userProfileData")?.dataset?.userRole;

    if (userId) {
        loadUserReservations(userId);
    }

    if (userRole === "ADMIN") {
        loadAllReservations();
    }

    const editBtn = document.getElementById("editBtn");
    const saveBtn = document.getElementById("saveBtn");
    const cancelBtn = document.getElementById("cancelBtn");

    const viewMode = document.getElementById("viewMode");
    const editMode = document.getElementById("editMode");

    if (editBtn && saveBtn && cancelBtn && viewMode && editMode) {
        editBtn.addEventListener("click", () => {
            viewMode.style.display = "none";
            editMode.style.display = "block";
        });

        cancelBtn.addEventListener("click", () => {
            editMode.style.display = "none";
            viewMode.style.display = "block";
        });

        saveBtn.addEventListener("click", () => {
            const login = document.getElementById("loginInput").value;
            const email = document.getElementById("emailInput").value;
            const password = document.getElementById("passwordInput").value;
            const role = document.getElementById("editorCheckbox").checked ? "EDITOR" : "USER";

            fetch(`/users/${userId}`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ login, email, password, role })
            })
                .then(res => {
                    if (!res.ok) throw new Error("Failed to save changes.");
                    return res.json();
                })
                .then(data => {
                    document.getElementById("loginDisplay").innerText = data.login;
                    document.getElementById("emailDisplay").innerText = data.email;
                    document.getElementById("roleDisplay").innerText = data.role;

                    editMode.style.display = "none";
                    viewMode.style.display = "block";
                })
                .catch(err => {
                    alert("Error saving profile: " + err.message);
                });
        });
    }
});

function formatDate(isoDate) {
    if (!isoDate) return "—";
    return new Date(isoDate).toLocaleDateString("en-GB", {
        day: "2-digit",
        month: "short",
        year: "numeric"
    });
}

function renderStatusBadge(status) {
    const map = {
        PENDING: "warning",
        ACTIVE: "success",
        RETURNED: "secondary",
        REJECTED: "danger",
        OVERDUE: "danger"
    };
    const color = map[status] || "dark";
    return `<span class="badge bg-${color}">${status}</span>`;
}

function loadUserReservations(userId) {
    fetch(`/reservations/user/${userId}`)
        .then(res => res.json())
        .then(data => {
            const container = document.getElementById("reservationsContainer");
            if (!data || data.length === 0) {
                container.innerHTML = '<p>No reservations found.</p>';
                return;
            }

            const html = data.map(r => `
        <div class="card mb-3">
          <div class="card-body">
            <h5 class="card-title">${r.bookTitle}</h5>
            <p class="card-text mb-1">Due: <strong>${formatDate(r.dueDate)}</strong></p>
            <p class="card-text mb-2">Status: ${renderStatusBadge(r.status)}</p>
            <a href="/reservation-details?id=${r.id}" class="btn btn-outline-primary btn-sm">Details</a>
          </div>
        </div>`
            ).join("");
            container.innerHTML = html;
        })
        .catch(err => console.error("Failed to fetch user reservations:", err));
}

function loadAllReservations() {
    fetch(`/reservations`)
        .then(res => res.json())
        .then(data => {
            renderAdminReservations(data);
        });
}

function renderAdminReservations(reservations) {
    const container = document.getElementById("adminReservationsContainer");
    const statusFilter = document.getElementById("statusFilter").value;
    let filtered = statusFilter === "ALL"
        ? reservations
        : reservations.filter(r => r.status === statusFilter);

    if (filtered.length === 0) {
        container.innerHTML = '<p>No matching reservations.</p>';
        return;
    }

    const html = filtered.map(r => `
    <div class="card mb-3">
      <div class="card-body">
        <h5 class="card-title">${r.bookTitle}</h5>
        <p class="card-text mb-1">User: ${r.reserverName}</p>
        <p class="card-text mb-1">Due: <strong>${formatDate(r.dueDate)}</strong></p>
        <p class="card-text mb-2">Status: ${renderStatusBadge(r.status)}</p>
        ${r.status === "PENDING" ? `
          <button class="btn btn-success btn-sm me-2" onclick="approveReservation(${r.id})">Accept</button>
          <button class="btn btn-danger btn-sm" onclick="rejectReservation(${r.id})">Reject</button>
        ` : ""}
      </div>
    </div>
  `).join("");
    container.innerHTML = html;
}

function approveReservation(id) {
    fetch(`/reservations/${id}/approve`, {
        method: 'POST'
    })
        .then(() => loadAllReservations())
        .catch(err => alert("Failed to approve reservation."));
}

function rejectReservation(id) {
    fetch(`/reservations/${id}/reject`, {
        method: 'POST'
    })
        .then(() => loadAllReservations())
        .catch(err => alert("Failed to reject reservation."));
}

function filterReservations() {
    loadAllReservations();
}
