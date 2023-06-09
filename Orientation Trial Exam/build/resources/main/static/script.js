
// DOM loading
document.addEventListener("DOMContentLoaded", () => {

    // =====================================
    // ========== Create new Post ==========
    // =====================================

    // Get the form element
    const newPostForm = document.querySelector(".form");

    // Add a submit event listener to the form
    newPostForm.addEventListener("submit", (event) => {
        event.preventDefault();

        // Get the entered title and URL values
        const url = newPostForm.querySelector('input[name="url"]').value;
        const alias = newPostForm.querySelector('input[name="alias"]').value;
        const lengthOfTheCode = 4;
        const secretCode = generateCode(lengthOfTheCode);

        // Fetch data to backend
        const formData = {
            url: url,
            alias: alias,
            hitCount: 0,
            secretCode: secretCode
        };

        fetch("http://localhost:8080/api/links", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
            .then(response => response.text())
            .then(data => {
                console.log(data);
                console.log("Thank you, you've successfully created a Post");

                // Show the success message
                const message = document.querySelector(".message");
                message.textContent = `Your URL is aliased to ${alias} and your secret code is ${secretCode}`;

                // Reset the form fields
                newPostForm.reset();
            })
            .catch(error => {
                console.error("Error:", error);

                // Show the error message
                const message = document.querySelector(".message");
                message.style.color = "red";
                message.textContent = "Your alias is already in use!";
            });
    });

    // Function to generate a secret code based od length provided
    function generateCode(length) {
        const message = document.querySelector(".message");
        message.text = "";
        message.style.color = "";
        let result = "";
        const characters = "0123456789";
        const charactersLength = characters.length;
        let counter = 0;
        while (counter < length) {
            result += characters.charAt(Math.floor(Math.random() * charactersLength));
            counter += 1;
        }
        return result;
    }
});
