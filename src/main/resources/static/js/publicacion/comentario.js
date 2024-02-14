$(document).ready(function(){
    let contenedor = document.getElementById("idCartel");
    let idCartel = contenedor.getAttribute("data-idCartel");
    $('#enviarComentario').prop('disabled', true).css('background-color', 'gray');
    mostrarComentarios(idCartel);
    

});

//Enviar comentario
$(document).ready(function() {
    $('body').on('click','#enviarComentario', function() {
        let data_comentario = $('#comentario').val();
       	let contenedor = document.getElementById("idCartel");
    	let idCartel = contenedor.getAttribute("data-idCartel");
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        // Verificar si el comentario está vacío
        if (data_comentario.trim() === "") {
            // Si está vacío, no realizar la petición y mostrar un mensaje
            console.log("El comentario está vacío.");
            return;
        }

        //Hace una petición al backend para guardar el comentario
        $.ajax({
            type: "POST",
            url: "/comentarios?idCartel="+idCartel,
            dataType: "json",
            data: { esteComentario: data_comentario},
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function(response) {
                mostrarComentarios(idCartel);
                $('#comentario').val('');
                $('#enviarComentario').prop('disabled', true).css('background-color', 'gray');
            },
            error: function(error) {
                console.error(error);
                alert("Error al guardar el comentario.");
            }
        });
    });
});

//Borrar comentario
$(document).ready(function() {
    $('body').on('click','#borrarComentario', function() {
		
        let idComentario = $(this).attr('data-idComentario');
        let nick = $(this).attr('data-nick');
        let contenedor = document.getElementById("idCartel");
    	let idCartel = contenedor.getAttribute("data-idCartel");

    
        $.ajax({
            url: "/borrarComentarios?idComentario="+idComentario+"&nick="+nick,
            method: "GET",
            success: function(response) {
                mostrarComentarios(idCartel)
            },
            error: function(error) {
                console.error(error);
                alert("Error al borrar el comentario.");
            }
        });
    });
});


//Likes en los comentarios
$(document).ready(function() {
    $('body').on('click','#meGusta', function() {
		
        let idComentario = $(this).attr('data-idComentario');
        let nick = $(this).attr('data-nick');
        
        let contenedor = document.getElementById("idCartel");
    	let idCartel = contenedor.getAttribute("data-idCartel");

    
        $.ajax({
            url: "/likeComentarios?idComentario="+idComentario+"&nick="+nick,
            method: "GET",
            success: function(response) {
                mostrarComentarios(idCartel)
            },
            error: function(error) {
                console.error(error);
                alert("Error al dar me gusta.");
            }
        });
    });
});




//Mostrar comentarios
function mostrarComentarios(idCartel) {
    $.ajax({
        url: '/cajaComentarios?idCartel='+idCartel,
        method: 'GET',
        success: function(resultText) {
            $("#cajaComentarios").html(resultText);
        }
    });
}



// Cambia el color y desactiva o activa del boton enviarComentario, dependiendo si hay algo escrito en el input del comentario
$('#comentario').on('input', function() {
    let comentario = $(this).val().trim();
    if (comentario === "") {
        $('#enviarComentario').prop('disabled', true).css('background-color', 'gray');
    } else {
        $('#enviarComentario').prop('disabled', false).css('background-color', ''); 
    }
});