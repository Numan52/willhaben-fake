function previewImages(event) {

    const previewContainer = document.getElementById("image-preview-container")
    previewContainer.innerHTML = ""

    const files = event.target.files

    for(const file of files){
        const reader = new FileReader();

        reader.onload = function(event) {
            const image = document.createElement("img");
            image.src = event.target.result
            image.className = "preview-image"
            previewContainer.appendChild(image)
        }
        reader.readAsDataURL(file);
    }
}