import {getCurrentUserId} from "./userAuthentication.js";
import {displaySingleMessage, sendMessage} from "./messageUtility.js";

document.addEventListener("DOMContentLoaded", async function () {
    await displayInteractedUsers()
    createTextField()
})


async function displayInteractedUsers() {
    try {
        const response = await fetch("/messages/get-interacted-users")
        const users = await response.json()
        console.log(users)

        for (const user of users) {
            if (user.id !== await getCurrentUserId()) {
                usersColumn(user)
            }

        }
    } catch (error) {
        console.error(error)
    }

}



async function displayConversation(user) {
    try {
        const response = await fetch(`/messages/get-conversation/${user.id}`)
        const messageObjects = await response.json()
        console.log(messageObjects)
        const chatContainer = document.querySelector(".chat");
        chatContainer.innerHTML = ""; // Clear existing messages


        const currentUsername = document.createElement("div")
        currentUsername.textContent = user.username
        currentUsername.className = "current-username"
        chatContainer.appendChild(currentUsername)

        for (const messageObj of messageObjects) {
            console.log(`messageObj: ${messageObj}`)
            await displaySingleMessage(messageObj)
        }
    } catch (error) {
        console.log(error)
    }
}

function createTextField() {
    const textInput = document.querySelector("#message-input")
    const sendButton = document.querySelector("#send-message-button")

    sendButton.addEventListener("click", async function(event)  {
        event.preventDefault()
        const selectedUser = document.querySelector(".user-container.active")
        const userId = selectedUser.dataset.userId
        await sendMessage("message-input", userId)
    })
}



function usersColumn(user) {
    const usersContainer = document.querySelector(".users")

    const userContainer = document.createElement("a")
    userContainer.className = "user-container"
    userContainer.href = "#"
    userContainer.dataset.userId = user.id
    userContainer.addEventListener("click", async function(event) {
        event.preventDefault();
        document.querySelectorAll(".user-container").forEach(container =>  {
            container.classList.remove("active")
            container.style.backgroundColor = ""
        })
        userContainer.classList.add("active");
        userContainer.style.backgroundColor = "#afafb2"
        await displayConversation(user)
    })

    const profilePicture = document.createElement("img")
    profilePicture.className = "profile-picture"
    profilePicture.src = "/images/user-profile-icon.png"

    const name = document.createElement("div")
    name.className = "username"
    name.textContent = user.username


    userContainer.appendChild(profilePicture)
    userContainer.appendChild(name)
    usersContainer.appendChild(userContainer)
}

