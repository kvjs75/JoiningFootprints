$(document).ready(function() {
    $('#enviar1').on('click', function() {
        var contenedorCartel = $("#cartel")[0];

        //Convierte en una imagen (canvas) el div seleccionado
        html2canvas(contenedorCartel).then(function(canvas) {
            let estaImagen = canvas.toDataURL("image/png");
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");

            //Hace una petición al backend para guardar la imagen
            $.ajax({
                type: "POST",
                url: "/paso1_2",
                data: JSON.stringify({ "estaImagen": estaImagen }),
                contentType: "application/json",
                beforeSend: function(request) {
                    request.setRequestHeader(header, token);
                },
                success: function(response) {
                   console.log(response);
                    
                    //una vez guardado correctamente, redireccina a la confirmacion
                    window.location.href = "/publicar_confirmacion?confirmacion=true";
                },
                error: function(error) {
                    console.error(error);
                    alert("Error al guardar la imagen.");
                }
            });
        });
    });
});


$(document).ready(function() {
    $('#enviar2').on('click', function() {
        var contenedorCartel = $("#cartel")[0];

        //Convierte en una imagen (canvas) el div seleccionado
        html2canvas(contenedorCartel).then(function(canvas) {
            let estaImagen = canvas.toDataURL("image/png");
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");

            //Hace una petición al backend para guardar la imagen
            $.ajax({
                type: "POST",
                url: "/paso2_2",
                data: JSON.stringify({ "estaImagen": estaImagen }),
                contentType: "application/json",
                beforeSend: function(request) {
                    request.setRequestHeader(header, token);
                },
                success: function(response) {
                    console.log(response);
                    
                    //una vez guardado correctamente, redireccina a la confirmacion
                    window.location.href = "/publicar_confirmacion?confirmacion=true";
                },
                error: function(error) {
                    console.error(error);
                    alert("Error al guardar la imagen.");
                }
            });
        });
    });
});