import {sendMessage} from "./messageUtility.js";
import {getProductID, getSellerUserId} from "./userAuthentication.js";

let productImages
let currentSlideIndex = 0
let imageSlides
document.addEventListener("DOMContentLoaded", function () {
    imageSlides = document.querySelector(".image-slides")

    const sendMessageButton = document.querySelector(".send-message")
    const messageInput = document.querySelector("#message")
    sendMessageButton.addEventListener("click", async function (event) {
        event.preventDefault()
        await sendMessage(messageInput.id, await getSellerUserId())
    })

    getProduct()

})


function getProduct() {
    const productId = getProductID()
    fetch(`/products/product-details?id=${productId}`)
        .then(response => {
            if (response.status === 404) {
                console.error("Product not found")
                alert("Product not found. Redirecting to the home page.")
                window.location.href = "/"
            } else if (response.ok) {
                return response.json().then(product => {
                    displayProduct(product)
                })
            }
        }).catch(error => {
        console.log(error)
        alert(`${error}. Please try again later.`)
    })
}


function displaySlide() {
    imageSlides.textContent = ""
    const slide = document.createElement("div")
    slide.className = "slide"
    const image = document.createElement("img")
    image.className = "product-image"
    image.src = "data:image/png;base64," + productImages[currentSlideIndex].imageData
    slide.appendChild(image)

    imageSlides.appendChild(slide)
}

function nextSlide() {
    currentSlideIndex = (currentSlideIndex + 1) % (productImages.length)
    displaySlide()
}

function prevSlide() {
    currentSlideIndex = (currentSlideIndex - 1 + productImages.length) % (productImages.length)
    displaySlide()
}




/*
async function sendMessage() {
    const inputField = document.querySelector("#message")
    const message = inputField.value
    inputField.value = ""

    const currentUserId = await getCurrentUserId();
    const sellerUserId = await getSellerUserId();

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
        console.log("Message was sent successfully")
    })
        .catch(error => console.log("Error occurred: " + error))
}

*/





function displayProduct(product) {
    console.log(product)
    productImages = product.images
    displaySlide()

    const priceContainer = document.querySelector(".price-container")
    const price = document.createElement("span")
    price.textContent = `€ ${product.price}`
    price.className = "product-price"
    priceContainer.appendChild(price)

    const usernameContainer = document.querySelector(".username-container")
    const profilePicture = document.createElement("img")
    profilePicture.src = "/images/user-profile-icon.png"
    profilePicture.className = "profile-picture"
    const username = document.createElement("span")
    username.textContent = product.username
    username.className = "username"
    usernameContainer.appendChild(profilePicture)
    usernameContainer.appendChild(username)

    const messageContainer = document.querySelector(".message-container")
    const messageButton = document.createElement("button")
    messageButton.textContent = "Message"
    messageContainer.appendChild(messageButton)

    const descriptionContainer = document.querySelector(".product-description")
    const productDescription = document.createElement("p")
    productDescription.textContent = product.description
    descriptionContainer.appendChild(productDescription)

}
