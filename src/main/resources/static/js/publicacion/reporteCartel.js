$(document).ready(function(){
    $('#realizarReporteCartel').prop('disabled', true).css('background-color', 'gray');
});

$(document).ready(function() {
    $('body').on('click','#realizarReporteCartel', function() {
    	let data_motivo = $('#causaReporteCartel').val();
    	let idUsuario= $(this).attr('data-idUsuario');
    	let contenedor = document.getElementById("idCartel");
    	let idCartel = contenedor.getAttribute("data-idCartel");
    	let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        
        $.ajax({
            type: "POST",
            url: "/reportarCartel?idUsuario="+idUsuario+"&idCartel="+idCartel,
            dataType: "json",
            data: {motivoCartel: data_motivo},
            beforeSend: function(request) {
                request.setRequestHeader(header, token);
            },
            success: function(response) {
				window.location.href = "/publicacion?idCartel="+idCartel+"&reporteExitoso=true";
                $('#causaReporteCartel').val('');
            },
            error: function(error) {
                console.error(error);
                alert("Error al reportar el comentario.");
            }
        });
    });
});

$('#causaReporteCartel').on('input', function() {
    let comentario = $(this).val().trim();
    if (comentario === "") {
        $('#realizarReporteCartel').prop('disabled', true).css('background-color', 'gray');
    } else {
        $('#realizarReporteCartel').prop('disabled', false).css('background-color', ''); 
    }
});