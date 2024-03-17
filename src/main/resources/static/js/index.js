import {updatedHeader} from "./header.js";

document.addEventListener("DOMContentLoaded", function() {
    getAllProducts()
    updatedHeader()


})

function getAllProducts() {
    fetch("/products/all")
        .then(response => {
            return response.json()
        })
        .then(products => {
            console.log(products)
            displayProducts(products)
        })
        .catch(error => console.error("Error fetching products", error))
}



// document.addEventListener("userLoggedIn", function (event) {
//     const username = event.detail.username;
//     updateHeaderAfterLogin(username);
// })



/*
function updateHeaderAfterLogin(username) {
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
    name.textContent = username
    name.className = "username"


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
*/


function displayProducts(products) {
    const productsContainer = document.querySelector(".products-container")
    products.forEach(product => {
        const productCard = createProductCard(product)
        productsContainer.appendChild(productCard)
    })
}

function createProductCard(product) {
    const productContainer = document.createElement("div");
    productContainer.className = "product-container"
    const link = document.createElement("a")
    link.href = `product-details?id=${product.id}`

    const image = document.createElement("img")
    image.className = "product-image"
    console.log(product)
    image.src = "data:image/png;base64," + product.images[0].imageData

    const title = document.createElement("div")
    title.className = "product-title"
    title.textContent = product.title

    const price = document.createElement("div")
    price.className = "product-price"
    price.textContent = "€ " + product.price


    productContainer.appendChild(image)
    productContainer.appendChild(title)
    productContainer.appendChild(price)
    link.appendChild(productContainer)

    return link
}
