// DOM loading
document.addEventListener("DOMContentLoaded", () => {
    // Get the form element
    const newPostForm = document.querySelector(".addMessage");
    const refreshButton = document.querySelector(".refresh");
    const listUl = document.querySelector(".list ul");

    // Function to create message elements
    function createMessageElement(message) {
        const { username, text } = message;

        // Create the elements for the message
        const li = document.createElement("li");
        const userNameParagraph = document.createElement("p");
        userNameParagraph.textContent = username;
        userNameParagraph.style.fontWeight = "bold";
        const textParagraph = document.createElement("p");
        textParagraph.textContent = text;

        li.appendChild(userNameParagraph);
        li.appendChild(textParagraph);

        listUl.appendChild(li);
    }

    // Function to refresh page content
    function refreshPageContent() {
        // Fetch data from the backend
        fetch("/messages", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then((response) => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error("An error occurred while fetching messages.");
                }
            })
            .then((data) => {
                // Clear the existing list
                listUl.textContent = "";

                // Create message elements for each message in the response data
                data.forEach((message) => {
                    createMessageElement(message);
                });

                // Check if there are no messages and display the initial content
                if (data.length === 0) {
                    createMessageElement({
                        username: "App",
                        text: "Hi there! Submit your message using the send button!",
                    });
                }
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    }

    // Add a submit event listener to the form
    newPostForm.addEventListener("submit", (event) => {
        event.preventDefault();

        // Get the entered title and URL values
        const message = newPostForm.querySelector('textarea[name="message"]').value;
        const user = newPostForm.querySelector('input[name="user"]').value;

        // Create the form data object
        const formData = {
            username: user,
            text: message
        };

        // Send the form data to the backend for message creation
        fetch("/messages", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(formData),
        })
            .then((response) => {
                if (response.ok) {
                    return response.json();
                } else if (response.status === 400) {
                    throw new Error("An Error occurred!");
                }
            })
            .then((data) => {
                console.log("You provided data for Message creation");
                console.log(data);

                if (Array.isArray(data)) {
                    data.message.forEach((message) => {
                        createMessageElement(message);
                    });
                } else {
                    createMessageElement(data.message);
                }

                // Reset the form fields
                newPostForm.reset();
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    });

    // Add a click event listener to the refresh button
    refreshButton.addEventListener("click", (event) => {
        event.preventDefault();
        refreshPageContent();
    });

    // Refresh the page content initially
    refreshPageContent();
});
