function openGmailTab(emailAddress) {
    let url = "https://mail.google.com/mail/u/0/?authuser=" + emailAddress;
    window.open(url, "_blank");
}