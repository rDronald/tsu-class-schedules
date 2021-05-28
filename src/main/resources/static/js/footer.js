function setUI() {
    const header = document.getElementById("div-header");
    const footer = document.getElementById("div-footer");

    footer.style.height = header.style.height;
    footer.className = header.className;
}