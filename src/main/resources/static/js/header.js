import {handleLogout} from "./userAuthentication.js";

class Header extends HTMLElement {
    constructor() {
        super();
    }

    connectedCallback() {
        this.innerHTML = `
            
        `
    }
}

function updatedHeader() {
    const loggedInUser = JSON.parse(localStorage.getItem("loggedInUser"))
    if (loggedInUser) {
        const container = document.querySelector(".login-register-container")
        container.textContent = ""

        const messagesLink = document.createElement("a")
        messagesLink.href = "/messages/get-messages"
        messagesLink.textContent = "Messages"
        messagesLink.className = "messages-link"

        const userLink = document.createElement("a")
        userLink.href = "#"
        userLink.className = "user-container"
        userLink.addEventListener("click", loadProfilePage)

        const image = document.createElement("img")
        image.src = "/images/user-profile-icon.png"
        image.className = "profile-pic"

        const name = document.createElement("strong")
        name.textContent = loggedInUser.username
        name.className = "username"
        console.log(loggedInUser)

        const logout = document.createElement("a")
        logout.href = "#"
        logout.addEventListener("click", handleLogout)
        logout.textContent = "Logout"


        userLink.appendChild(image)
        userLink.appendChild(name)
        container.appendChild(messagesLink)
        container.appendChild(userLink)
        container.appendChild(logout)
    }


}

function loadProfilePage() {
    window.location.href = "/my-profile"
}



export {updatedHeader}