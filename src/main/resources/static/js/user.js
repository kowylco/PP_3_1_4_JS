const userUrl = document.URL + "api/user"

const principalUsername = document.querySelector("#principal-username")
const principalRoles = document.querySelector("#principal-roles")

fetch(userUrl)
    .then(response => response.json())
    .then(user => {
        document.querySelector("#id").textContent = user.id
        document.querySelector("#firstname").textContent = user.firstname
        document.querySelector("#lastname").textContent = user.lastname
        document.querySelector("#age").textContent = user.age
        document.querySelector("#email").textContent = user.email
        principalUsername.textContent = user.email
        console.log(user.roles)
        let roles = ""
        for (let role of user.roles) {
            console.log(role.name)
            roles += role.name.replace("ROLE_", "") + " "
        }
        principalRoles.textContent = roles
        document.querySelector("#roles").textContent = roles
    })