async function getCurrentUserId() {
    const result = await fetch("/users/get-current-userId")
    const data = await result.json()
    return data.userId

}

async function getSellerUserId() {
    const productId = getProductID()
    try {
        const response = await fetch(`/users/get-userId-by-productId?productId=${productId}`)
        const data = await response.json()
        const userID = data.userId
        console.log("Seller ID adasdas: " + userID)
        return userID
    } catch (error) {
        console.log("Error occurred: " + error)
        return null
    }
}

function getProductID(){
    const queryParams = new URLSearchParams(window.location.search);
    return queryParams.get("id");
}

function handleLogout(event) {
    event.preventDefault()

    fetch("/logout")
        .then(response => {
            localStorage.clear()
            window.location.href = "/"
        })
        .catch(error => console.log(error))
}

export {getCurrentUserId, getSellerUserId, getProductID, handleLogout}