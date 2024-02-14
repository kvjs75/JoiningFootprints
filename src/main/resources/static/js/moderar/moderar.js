$(document).ready(function(){
	contadorCartelesPendienteModerador();
	contadorCartelesDenunciadoModerador();
	contadorComentarioDenunciadoModerador();
    mostrarCartelesPendientes();
    mostrarCartelesReportados();
    mostrarComentariosesReportados();
});

$(document).ready(function() {
    $('body').on('click','#aprobar', function() {
		
        let idCartel = $(this).attr('data-idCartel');

    
        $.ajax({
            url: "/aprobarCartel?idCartel="+idCartel,
            method: "GET",
            success: function(response) {
				contadorCartelesPendienteModerador()
                mostrarCartelesPendientes()
            },
            error: function(error) {
                console.error(error);
                alert("Error al aprobar el cartel.");
            }
        });
    });
});

$(document).ready(function() {
    $('body').on('click','#rechazar', function() {
		
        let idCartel = $(this).attr('data-idCartel');

    
        $.ajax({
            url: "/rechazarCartel?idCartel="+idCartel,
            method: "GET",
            success: function(response) {
				contadorCartelesPendienteModerador()
                mostrarCartelesPendientes()
            },
            error: function(error) {
                console.error(error);
                alert("Error al rechazar el cartel.");
            }
        });
    });
});


$(document).ready(function() {
    $('body').on('click','#botonResueltoReporteCartel', function() {
		
        let idReporteCartel = $(this).attr('data-idReporteCartel');

    
        $.ajax({
            url: "/marcarReporteResueltoCartel?idReporteCartel="+idReporteCartel,
            method: "GET",
            success: function(response) {
				contadorCartelesDenunciadoModerador()
                mostrarCartelesReportados();
            },
            error: function(error) {
                console.error(error);
                alert("Error al marcar reporte de cartel resuelto.");
            }
        });
    });
});


$(document).ready(function() {
    $('body').on('click','#botonResueltoReporteComentario', function() {
		
        let idReporteComentario = $(this).attr('data-idReporteComentario');

    
        $.ajax({
            url: "/marcarReporteResueltoComentario?idReporteComentario="+idReporteComentario,
            method: "GET",
            success: function(response) {
				contadorComentarioDenunciadoModerador()
                mostrarComentariosesReportados();
            },
            error: function(error) {
                console.error(error);
                alert("Error al marcar reporte de comentario resuelto.");
            }
        });
    });
});

function contadorCartelesPendienteModerador() {
    $.ajax({
        url: '/contadorCartelesPendienteModerador',
        method: 'GET',
        success: function(resultText) {
            $("#contadorCartelesPendienteModerador").html("("+resultText+")");
        }
    });
}

function contadorCartelesDenunciadoModerador() {
    $.ajax({
        url: '/contadorCartelesDenunciadoModerador',
        method: 'GET',
        success: function(resultText) {
            $("#contadorCartelesDenunciadoModerador").html("("+resultText+")");
        }
    });
}

function contadorComentarioDenunciadoModerador() {
    $.ajax({
        url: '/contadorComentarioDenunciadoModerador',
        method: 'GET',
        success: function(resultText) {
            $("#contadorComentarioDenunciadoModerador").html("("+resultText+")");
        }
    });
}

function mostrarCartelesPendientes() {
    $.ajax({
        url: '/mostrarCartelesPendientes',
        method: 'GET',
        success: function(resultText) {
            $("#listaCarteles").html(resultText);
        }
    });
}

function mostrarCartelesReportados() {
    $.ajax({
        url: '/mostrarCartelesDenunciado',
        method: 'GET',
        success: function(resultText) {
            $("#listaCartelesReportados").html(resultText);
        }
    });
}

function mostrarComentariosesReportados() {
    $.ajax({
        url: '/mostrarComentarioDenunciado',
        method: 'GET',
        success: function(resultText) {
            $("#listaComentariosReportados").html(resultText);
        }
    });
}