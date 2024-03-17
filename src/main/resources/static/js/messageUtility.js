import {getCurrentUserId} from "./userAuthentication.js";

async function sendMessage(inputFieldId, otherUserId) {
    const inputField = document.querySelector(`#${inputFieldId}`)
    const message = inputField.value
    inputField.value = ""

    const currentUserId = await getCurrentUserId();
    const sellerUserId = otherUserId;

    const messageDto = {
        senderId: currentUserId,
        receiverId: sellerUserId,
        content: message
    }

    console.log("Current User ID: " + currentUserId)
    console.log("Seller ID: " + sellerUserId)
    fetch("/messages/send-message", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(messageDto)
    }).then(response => {
        if (!response.ok) {
            throw new Error(`Failed to send message: ${response.statusText}`)
        }
        displaySingleMessage(messageDto)
        console.log("Message was sent successfully")
    })
        .catch(error => console.log("Error occurred: " + error))
}

async function displaySingleMessage(messageObj) {
    const chatContainer = document.querySelector(".chat")
    const message = document.createElement("div")
    message.className = "message"
    message.textContent = messageObj.content

    try {
        const currentUserId = await getCurrentUserId()
        if (messageObj.senderId === currentUserId) {
            message.id = "your-message"
        } else {
            message.id = "their-message"
        }
    } catch (error) {
        console.log(error)
    }

    chatContainer.appendChild(message)
    chatContainer.scrollTop = chatContainer.scrollHeight
}

export{sendMessage, displaySingleMessage}