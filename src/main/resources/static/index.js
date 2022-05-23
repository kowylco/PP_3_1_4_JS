const usersUrl = document.URL + "api/admin/users"
const userUrl = document.URL + "api/user"
console.log(usersUrl)
console.log(userUrl)

const principalUsername = document.querySelector("#principal-username")
const principalRoles = document.querySelector("#principal-roles")
const usersTable = document.querySelector("#users-table")
const editModal = new bootstrap.Modal(document.querySelector("#editModal"))
const deleteModal = new bootstrap.Modal(document.querySelector("#deleteModal"))
let editButtons
let deleteButtons

fetch(userUrl)
    .then(response => response.json())
    .then(user => {
        principalUsername.textContent = user.email
        console.log(user.roles)
        for (let role of user.roles) {
            console.log(role.name)
            principalRoles.textContent += role.name.replace("ROLE_", "") + " "
        }
    })

async function fillUsersTable() {
await fetch(usersUrl)
    .then(response => response.json())
    .then(users => {
        usersTable.innerHTML = ""
        for (let user of users) {
            let userRoles = ""
            for (let role of user.roles) {
                userRoles += role.name.replace("ROLE_", "") + " "
            }
            usersTable.innerHTML += `
            <tr>
                <td>${user.id}</td>
                <td>${user.firstname}</td>
                <td>${user.lastname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>
                    <span >
                        <span>${userRoles}</span>
                    </span>
                </td>
                <td>
                    <button type="button" class="editbtn btn btn-primary">
                        Edit
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger deletebtn" data-bs-toggle="modal">
                        Delete
                    </button>
                </td>
            </tr>
            
            `
        }
    })
}
fillUsersTable().then(() => {
    editButtons = document.querySelectorAll(".editbtn")
    deleteButtons = document.querySelectorAll(".deletebtn")
    editButtons.forEach((btn) => {
        console.log(btn);
        let id = btn.parentElement.parentElement.childNodes[1].textContent
        console.log(id);
        btn.addEventListener("click", () => {
            showEditModal(id)
        })
    })
    deleteButtons.forEach((btn) => {
        console.log(btn);
        let id = btn.parentElement.parentElement.childNodes[1].textContent
        console.log(id);
        btn.addEventListener("click", () => {
            showDeleteModal(id)
        })
    })
    console.log(editButtons)
    console.log(deleteButtons)
})

function showEditModal(id) {
    fetch(usersUrl + "/" + id)
        .then(response => response.json())
        .then(user => {
            document.querySelector("#edit_id").setAttribute("value", user.id)
            document.querySelector("#edit_firstname").setAttribute("value", user.firstname)
            document.querySelector("#edit_lastname").setAttribute("value", user.lastname)
            document.querySelector("#edit_age").setAttribute("value", user.age)
            document.querySelector("#edit_email").setAttribute("value", user.email)
            document.querySelector("#edit_password").setAttribute("value", user.password)
        })
    editModal.show()
}

function showDeleteModal(id) {
    fetch(usersUrl + "/" + id)
        .then(response => response.json())
        .then(user => {
            document.querySelector("#delete_id").setAttribute("value", user.id)
            document.querySelector("#delete_firstname").setAttribute("value", user.firstname)
            document.querySelector("#delete_lastname").setAttribute("value", user.lastname)
            document.querySelector("#delete_age").setAttribute("value", user.age)
            document.querySelector("#delete_email").setAttribute("value", user.email)
            document.querySelector("#delete_password").setAttribute("value", user.password)
        })
    deleteModal.show()
}

