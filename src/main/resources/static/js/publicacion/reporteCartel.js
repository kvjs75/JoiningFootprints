//En principio el boton de enviar motivo esta en gris y desactivado. hasta que se escriba algo
$(document).ready(function(){
    $('#realizarReporteCartel').prop('disabled', true).css('background-color', 'gray');
});


$(document).ready(function() {
    $('body').on('click','#realizarReporteCartel', function() {
    	
    	let data_motivo = $('#causaReporteCartel').val();
    	
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
            url: "/reportarCartel?idUsuario="+idUsuario+"&idCartel="+idCartel,
            dataType: "json",
            data: {motivoCartel: data_motivo},
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function(response) {
				//redirecciona a la mismo publicación del cartel con una alerta avisando que ha sido un exito el reporte
				window.location.href = "/publicacion?idCartel="+idCartel+"&reporteExitoso=true";
				//Se vacia el input del reporte cartel
                $('#causaReporteCartel').val('');
            },
            error: function(error) {
                console.error(error);
                alert("Error al reportar el comentario.");
            }
        });
    });
});

// Cambia el color y desactiva o activa del boton reportar, dependiendo si hay algo escrito en el input del reporte
$('#causaReporteCartel').on('input', function() {
    let comentario = $(this).val().trim();
    if (comentario === "") {
        $('#realizarReporteCartel').prop('disabled', true).css('background-color', 'gray');
    } else {
        $('#realizarReporteCartel').prop('disabled', false).css('background-color', ''); 
    }
});