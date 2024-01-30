$(document).ready(function() {
    $('#publicar').on('click', function() {
        // Selecciona el div en donde se aloja la maquetación del cartel
        var contenedorCartel = $("#cartel")[0];

        // Utiliza html2canvas para convertir el div en una imagen
        html2canvas(contenedorCartel).then(function(canvas) {
        // Crea un elemento de imagen y establece la imagen generada como fuente
        var imagen = new Image();
        imagen.src = canvas.toDataURL("image/png");

        // Agrega la imagen al documento o realiza alguna acción con ella
        document.body.appendChild(imagen);
        });
    });
});