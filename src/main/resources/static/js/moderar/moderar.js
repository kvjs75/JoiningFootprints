$(document).ready(function(){
	//inicializar los contadores
	contadorCartelesPendienteModerador();
	contadorCartelesDenunciadoModerador();
	contadorComentarioDenunciadoModerador();
	//inicializar las subvistas
    mostrarCartelesPendientes();
    mostrarCartelesReportados();
    mostrarComentariosesReportados();
});

//Boton para aprobar carteles
$(document).ready(function() {
    $('body').on('click','#aprobar', function() {
	
		//la ID del cartel se saca de la array de carteles
		//Al pulsar el boton respectivo del cartel se saca la ID de ese cartel	
        let idCartel = $(this).attr('data-idCartel');

        $.ajax({
            url: "/aprobarCartel?idCartel="+idCartel,
            method: "GET",
            success: function(response) {
				//Se actualiza el contador para descrementarlo
				contadorCartelesPendienteModerador()
				//Se actualiza la subvista para que desaparezca ese cartel
                mostrarCartelesPendientes()
            },
            error: function(error) {
                console.error(error);
                alert("Error al aprobar el cartel.");
            }
        });
    });
});

//Boton para rechazar carteles
$(document).ready(function() {
    $('body').on('click','#rechazar', function() {
		
		//la ID del cartel se saca de la array de carteles
		//Al pulsar el boton respectivo del cartel se saca la ID de ese cartel	
        let idCartel = $(this).attr('data-idCartel');

        $.ajax({
            url: "/rechazarCartel?idCartel="+idCartel,
            method: "GET",
            success: function(response) {
				//Se actualiza el contador para descrementarlo
				contadorCartelesPendienteModerador()
				//Se actualiza la subvista para que desaparezca ese cartel
                mostrarCartelesPendientes()
            },
            error: function(error) {
                console.error(error);
                alert("Error al rechazar el cartel.");
            }
        });
    });
});

//Boton para marcar la solicitud del reporte a CARTEL como resuelto
$(document).ready(function() {
    $('body').on('click','#botonResueltoReporteCartel', function() {
		
		//la ID de la solicitud de reporte cartel se saca de una array de reportes
		//Al pulsar el boton respectivo del reporte se saca la ID de ese reporte	
        let idReporteCartel = $(this).attr('data-idReporteCartel');

        $.ajax({
            url: "/marcarReporteResueltoCartel?idReporteCartel="+idReporteCartel,
            method: "GET",
            success: function(response) {
				//Se actualiza el contador para descrementarlo
				contadorCartelesDenunciadoModerador()
				//Se actualiza la subvista para que desaparezca esa solicitud de reporte
                mostrarCartelesReportados();
            },
            error: function(error) {
                console.error(error);
                alert("Error al marcar reporte de cartel resuelto.");
            }
        });
    });
});


//Boton para marcar la solicitud del reporte a COMENTARIO como resuelto
$(document).ready(function() {
    $('body').on('click','#botonResueltoReporteComentario', function() {
		
		//la ID de la solicitud de reporte COMENTARIO se saca de una array de reportes
		//Al pulsar el boton respectivo del reporte se saca la ID de ese reporte	
        let idReporteComentario = $(this).attr('data-idReporteComentario');

        $.ajax({
            url: "/marcarReporteResueltoComentario?idReporteComentario="+idReporteComentario,
            method: "GET",
            success: function(response) {
				//Se actualiza el contador para descrementarlo
				contadorComentarioDenunciadoModerador()
				//Se actualiza la subvista para que desaparezca esa solicitud de reporte
                mostrarComentariosesReportados();
            },
            error: function(error) {
                console.error(error);
                alert("Error al marcar reporte de comentario resuelto.");
            }
        });
    });
});

//funcion que devuelve el numero de solicitudes de carteles pendientes que hay actualmente
function contadorCartelesPendienteModerador() {
    $.ajax({
        url: '/contadorCartelesPendienteModerador',
        method: 'GET',
        success: function(resultText) {
            $("#contadorCartelesPendienteModerador").html("("+resultText+")");
        }
    });
}

//funcion que devuelve el numero de denuncias-carteles pendientes que hay actualmente
function contadorCartelesDenunciadoModerador() {
    $.ajax({
        url: '/contadorCartelesDenunciadoModerador',
        method: 'GET',
        success: function(resultText) {
            $("#contadorCartelesDenunciadoModerador").html("("+resultText+")");
        }
    });
}

//funcion que devuelve el numero de denuncias-comentarios pendientes que hay actualmente
function contadorComentarioDenunciadoModerador() {
    $.ajax({
        url: '/contadorComentarioDenunciadoModerador',
        method: 'GET',
        success: function(resultText) {
            $("#contadorComentarioDenunciadoModerador").html("("+resultText+")");
        }
    });
}

//Muestra los carteles de solicitudes pendientes (ModeradorController | controladora subvista: mostrarCartelesPendientes)
function mostrarCartelesPendientes() {
    $.ajax({
        url: '/mostrarCartelesPendientes',
        method: 'GET',
        success: function(resultText) {
			//se carga la subvista en un <ul>
            $("#listaCarteles").html(resultText);
        }
    });
}

//Muestra las solicitudes de reportes de carteles (ModeradorController | controladora subvista: mostrarCartelesDenunciado)
function mostrarCartelesReportados() {
    $.ajax({
        url: '/mostrarCartelesDenunciado',
        method: 'GET',
        success: function(resultText) {
			//se carga la subvista en un <ul>
            $("#listaCartelesReportados").html(resultText);
        }
    });
}

//Muestra las solicitudes de reportes de comentarios (ModeradorController | controladora subvista: mostrarComentarioDenunciado)
function mostrarComentariosesReportados() {
    $.ajax({
        url: '/mostrarComentarioDenunciado',
        method: 'GET',
        success: function(resultText) {
			//se carga la subvista en un <ul>
            $("#listaComentariosReportados").html(resultText);
        }
    });
}