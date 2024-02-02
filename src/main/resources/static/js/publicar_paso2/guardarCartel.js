$(document).ready(function() {
    $('#publicar').on('click', function() {
        var contenedorCartel = $("#cartel")[0];

        html2canvas(contenedorCartel).then(function(canvas) {
            let estaImagen = canvas.toDataURL("image/png");
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");

            $.ajax({
                type: "POST",
                url: "/guardarImagen",
                data: JSON.stringify({ "estaImagen": estaImagen }),
                contentType: "application/json",
                beforeSend: function(request) {
                    request.setRequestHeader(header, token);
                },
                success: function(response) {
                    console.log(response);
                    // Puedes manejar la respuesta del servidor aquí
                },
                error: function(error) {
                    console.error(error);
                    alert("Error al guardar la imagen.");
                }
            });
        });
    });
});