let idComentario = -1;

$(document).ready(function(){
    $('#realizarReporteComentario').prop('disabled', true).css('background-color', 'gray');
});

$(document).ready(function() {
    $('body').on('click','#botonReportar', function() {
        idComentario = $(this).attr('data-idComentario');
    });
});

$(document).ready(function() {
    $('body').on('click','#realizarReporteComentario', function() {
    	let data_motivo = $('#causaReporteComentario').val();
    	let idUsuario= $(this).attr('data-idUsuario');
    	let contenedor = document.getElementById("idCartel");
    	let idCartel = contenedor.getAttribute("data-idCartel");
    	let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        
        $.ajax({
            type: "POST",
            url: "/reportarComentarios?idUsuario="+idUsuario+"&idComentario="+idComentario,
            dataType: "json",
            data: {motivoComentario: data_motivo},
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function(response) {
				window.location.href = "/publicacion?idCartel="+idCartel+"&reporteExitoso=true";
                $('#causaReporteComentario').val('');
            },
            error: function(error) {
                console.error(error);
                alert("Error al reportar el comentario.");
            }
        });
    });
});

$('#causaReporteComentario').on('input', function() {
    let comentario = $(this).val().trim();
    if (comentario === "") {
        $('#realizarReporteComentario').prop('disabled', true).css('background-color', 'gray');
    } else {
        $('#realizarReporteComentario').prop('disabled', false).css('background-color', ''); 
    }
});

