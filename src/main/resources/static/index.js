const usersUrl = document.URL + "api/admin/users"
const userUrl = document.URL + "api/user"
console.log(usersUrl)
console.log(userUrl)

const principalUsername = document.querySelector("#principal-username")
const principalRoles = document.querySelector("#principal-roles")
const usersTable = document.querySelector("#users-table")

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

fetch(usersUrl)
    .then(response => response.json())
    .then(users => {
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
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal">
                        Edit
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger" data-bs-toggle="modal">
                        Delete
                    </button>
                </td>
            </tr>
            
            `
        }
    })
