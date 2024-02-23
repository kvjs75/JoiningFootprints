//Id del comentario reportado, al principio no se sabe cual es
let idComentario = -1;

//En principio el boton de enviar motivo esta en gris y desactivado. hasta que se escriba algo
$(document).ready(function(){
    $('#realizarReporteComentario').prop('disabled', true).css('background-color', 'gray');
});

//Cuando se pulsa el botonReportar, se asigna la id del comentario porque ya se sabe cual es
$(document).ready(function() {
    $('body').on('click','#botonReportar', function() {
        idComentario = $(this).attr('data-idComentario');
    });
});


$(document).ready(function() {
    $('body').on('click','#realizarReporteComentario', function() {
    	
    	let data_motivo = $('#causaReporteComentario').val();
    	
    	//el boton de enviar motivo, se almacena la id del usuario para saber quien lo ha realizado
    	let idUsuario= $(this).attr('data-idUsuario');
    	
    	//Se extrae el elemento mediante la id
    	let elementoImagen = document.getElementById("idCartel");
    	
    	//Con el elemento se recoje la variable idCartel
    	let idCartel = elementoImagen.getAttribute("data-idCartel");
    	
    	let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        
        //Hace una peticion al backend para guardar el reporte
        $.ajax({
            type: "POST",
            url: "/reportarComentarios?idUsuario="+idUsuario+"&idComentario="+idComentario,
            dataType: "json",
            data: {motivoComentario: data_motivo},
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function(response) {
				//redirecciona a la mismo publicación del cartel con una alerta avisando que ha sido un exito el reporte
				window.location.href = "/publicacion?idCartel="+idCartel+"&reporteExitoso=true";
				//Se vacia el input del reporte comentario
                $('#causaReporteComentario').val('');
            },
            error: function(error) {
                console.error(error);
                alert("Error al reportar el comentario.");
            }
        });
    });
});

// Cambia el color y desactiva o activa del boton reportar, dependiendo si hay algo escrito en el input del reporte
$('#causaReporteComentario').on('input', function() {
    let comentario = $(this).val().trim();
    if (comentario === "") {
        $('#realizarReporteComentario').prop('disabled', true).css('background-color', 'gray');
    } else {
        $('#realizarReporteComentario').prop('disabled', false).css('background-color', ''); 
    }
});

