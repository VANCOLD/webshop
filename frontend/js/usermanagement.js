// Check user privileges
function checkPrivileges() {
    // Get the user's token and privileges from localStorage
    const token = localStorage.getItem('token');
    const privileges = JSON.parse(localStorage.getItem('privileges'));

    // Check if the user has the required privileges
    if (privileges.includes('view_users') && privileges.includes('edit_profile')) {
        // User has the required privileges, proceed to load the user management page
        loadUserManagementPage();
    } else {
        // User does not have the required privileges, show an error message
        const errorMessage = "Access denied. Insufficient privileges.";
        displayErrorMessage(errorMessage);
    }
}

// Function to display error messages
function displayErrorMessage(message) {
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = message;
    document.body.appendChild(errorDiv);
}

// Load the user management page content
function loadUserManagementPage() {
    // You can load the content of the user management page here
    // For simplicity, let's display a message indicating that the user has access
    const userManagementContent = document.createElement('div');
    userManagementContent.textContent = "User Management Page - You have access!";
    document.body.appendChild(userManagementContent);
}

// Entry point - Check user privileges when the page loads
window.addEventListener('load', () => {
    checkPrivileges();
});
