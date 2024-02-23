$(document).ready(function(){
    let elementoImagen = document.getElementById("idCartel");
    let idCartel = elementoImagen.getAttribute("data-idCartel");
    
    //1) Al inicializar la paguina: el boton de enviar comentario esta en gris y desactivado. hasta que se escriba algo
    $('#enviarComentario').prop('disabled', true).css('background-color', 'gray');
    
    //2) Al inicializar la paguina: muestra todos los comentarios que tiene cartel mediante con una subvista
    mostrarComentarios(idCartel);
    

});

//Enviar comentario (controladora: comentarios)
$(document).ready(function() {
    $('body').on('click','#enviarComentario', function() {
        let data_comentario = $('#comentario').val();
       	let elementoImagen = document.getElementById("idCartel");
    	let idCartel = elementoImagen.getAttribute("data-idCartel");
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        //Verifica si el comentario esta vacio
        if (data_comentario.trim() === "") {
            //Si esta vacio, no realizar la peticion y mostrar un mensaje
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
                //una vez enviado, el input del comentario se vacia y se desactiva el boton de enviar
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

//Borrar comentario (controladora: borrarComentarios)
$(document).ready(function() {
    $('body').on('click','#borrarComentario', function() {
		
        let idComentario = $(this).attr('data-idComentario');
        let nick = $(this).attr('data-nick');
        let elementoImagen = document.getElementById("idCartel");
    	let idCartel = elementoImagen.getAttribute("data-idCartel");

    
        $.ajax({
            url: "/borrarComentarios?idComentario="+idComentario+"&nick="+nick,
            method: "GET",
            success: function(response) {
				//al borrar el comentario, se vuelve a recargar todos los comentario para actualizar
                mostrarComentarios(idCartel)
            },
            error: function(error) {
                console.error(error);
                alert("Error al borrar el comentario.");
            }
        });
    });
});


//Likes en los comentarios (controladora: likeComentarios)
$(document).ready(function() {
    $('body').on('click','#meGusta', function() {
		
        let idComentario = $(this).attr('data-idComentario');
        let nick = $(this).attr('data-nick');
        
        let elementoImagen = document.getElementById("idCartel");
    	let idCartel = elementoImagen.getAttribute("data-idCartel");

    
        $.ajax({
            url: "/likeComentarios?idComentario="+idComentario+"&nick="+nick,
            method: "GET",
            success: function(response) {
				//al darle like al comentario, se vuelve a recargar todos los comentario para actualizar
                mostrarComentarios(idCartel)
            },
            error: function(error) {
                console.error(error);
                alert("Error al dar me gusta.");
            }
        });
    });
});




//Mostrar comentarios (controladora subvista: cajaComentarios)
function mostrarComentarios(idCartel) {
    $.ajax({
        url: '/cajaComentarios?idCartel='+idCartel,
        method: 'GET',
        success: function(resultText) {
			//se carga la subvista en un <ul>
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