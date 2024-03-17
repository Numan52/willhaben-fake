import {updatedHeader} from "./header.js";
import {getCurrentUserId} from "./userAuthentication.js";

document.addEventListener("DOMContentLoaded", async function () {
    updatedHeader()
    await loadMyAds()

})


async function loadMyAds() {
    const myAdsContainer = document.querySelector(".myAds")
    myAdsContainer.innerHTML = ""
    const currentUser = await getCurrentUserId()
    fetch(`/products/get-user-products?userId=${currentUser}`)
        .then(response => response.json())
        .then(products => {
            if (products.length === 0) {
                noProductsMessage()
            } else {
                products.forEach(product => displayProduct(product))
            }
        })
        .catch(error => {
            console.log(error)
        })
}

function editItem() {

}

function deleteItem(productId, productContainer) {
    fetch(`/products/delete-product?productId=${productId}`, {
        method: "DELETE"
    })
        .then(response => response.text())
        .then(async result => {
            await loadMyAds()
            console.log("result " + result)
        })
        .catch(error => console.log("Error deleting product " + error))
}


function confirmDeleteDialog(_message, productId, productContainer) {
    const dialog = document.createElement("dialog")
    const message = document.createElement("p")

    const okButton = document.createElement("button")
    okButton.textContent = "Confirm"
    okButton.addEventListener("click", async () => {
        await deleteItem(productId, productContainer)
        dialog.close()
    })

    const cancelButton = document.createElement("button")
    cancelButton.textContent = "Cancel"
    cancelButton.addEventListener("click", () => {
        dialog.close()
    })

    message.textContent = _message
    dialog.appendChild(message)
    dialog.appendChild(okButton)
    dialog.appendChild(cancelButton)
    document.body.appendChild(dialog)

    dialog.showModal()

}


function noProductsMessage() {
    const container = document.querySelector(".myAds")
    const message = document.createElement("p")

    message.textContent = "Its empty in here"
    message.style.fontSize = "24px"
    container.appendChild(message)
}

function displayProduct(product) {
    const myAdsContainer = document.querySelector(".myAds")
    const productContainer = document.createElement("div")
    productContainer.className = "productContainer"

    const image = document.createElement("img")
    image.src = "data:image/png;base64," +  product.images[0].imageData
    image.className = "productImage"
    productContainer.appendChild(image)

    const productDetails = document.createElement("div")
    productDetails.className = "productDetails"
    const title = document.createElement("a")
    title.textContent = product.title
    title.className = "productTitle"
    title.href = `product-details?id=${product.id}`
    productDetails.appendChild(title)

    const price = document.createElement("div")
    price.textContent = "€ " + product.price
    price.className = "productPrice"
    productDetails.appendChild(price)

    productContainer.appendChild(productDetails)

    const editContainer = document.createElement("div")
    editContainer.className = "editContainer"

    const editButton = document.createElement("button")
    editButton.addEventListener("click", editItem)
    editButton.className = "editButton"
    const editImage = document.createElement("img")
    editImage.src = "/images/pencil-svgrepo-com.png"
    editImage.className = "editImage"
    editButton.appendChild(editImage)
    editContainer.appendChild(editButton)

    const deleteButton = document.createElement("button")
    deleteButton.addEventListener("click", () => confirmDeleteDialog("Are you sure you want to delete this item?", product.id, productContainer))
    deleteButton.className = "deleteButton"
    const deleteImage = document.createElement("img")
    deleteImage.src = "/images/delete-left-svgrepo-com.png"
    deleteImage.className = "deleteImage"
    deleteButton.appendChild(deleteImage)
    editContainer.appendChild(deleteButton)

    productContainer.appendChild(editContainer)
    myAdsContainer.appendChild(productContainer)
}