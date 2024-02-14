$(document).ready(function(){
	let contenedor = document.getElementById("nick");
    let nick = contenedor.getAttribute("data-nick");
    contadorCartelesActuales(nick)
    contadorCartelesResueltos(nick)
    contadorCartelesPendientes(nick)
    mostrarCartelesActuales(nick);
    mostrarCartelesResueltos(nick)
    mostrarCartelesPendientes(nick)
});

//boton resuelto
$(document).ready(function() {
    $('body').on('click','#botonResuelto', function() {
		let contenedor = document.getElementById("nick");
    	let nick = contenedor.getAttribute("data-nick");
        let idCartel = $(this).attr('data-idCartel');

        $.ajax({
            url: "/resolverCartel?idCartel="+idCartel,
            method: "GET",
            success: function(response) {
			   contadorCartelesActuales(nick)
			   contadorCartelesResueltos(nick)
    		   contadorCartelesPendientes(nick)
               mostrarCartelesActuales(nick)
               mostrarCartelesResueltos(nick)
            },
            error: function(error) {
                console.error(error);
                alert("Error al resolver el cartel.");
            }
        });
    });
});

//boton no resuelto
$(document).ready(function() {
    $('body').on('click','#botonNoResuelto', function() {
		let contenedor = document.getElementById("nick");
    	let nick = contenedor.getAttribute("data-nick");
        let idCartel = $(this).attr('data-idCartel');

        $.ajax({
            url: "/noResolverCartel?idCartel="+idCartel,
            method: "GET",
            success: function(response) {
				contadorCartelesActuales(nick)
				contadorCartelesResueltos(nick)
    			contadorCartelesPendientes(nick)
				mostrarCartelesResueltos(nick)
                mostrarCartelesActuales(nick)
            },
            error: function(error) {
                console.error(error);
                alert("Error al resolver el cartel.");
            }
        });
    });
});

//boton de borrar (utilizando la consulta y el controlador de rechazar del moderador. ya que hace lo mismo)
$(document).ready(function() {
    $('body').on('click','#botonBorrarActuales', function() {
		let contenedor = document.getElementById("nick");
    	let nick = contenedor.getAttribute("data-nick");
        let idCartel = $(this).attr('data-idCartel');

        $.ajax({
            url: "/borrarCartelesActuales?idCartel="+idCartel+"&nick="+nick,
            method: "GET",
            success: function(response) {
				contadorCartelesActuales(nick)
				contadorCartelesResueltos(nick)
    			contadorCartelesPendientes(nick)
				mostrarCartelesResueltos(nick)
                mostrarCartelesActuales(nick)
            },
            error: function(error) {
                console.error(error);
                alert("Error al resolver el cartel.");
            }
        });
    });
});

$(document).ready(function() {
    $('body').on('click','#botonBorrarResueltos', function() {
		let contenedor = document.getElementById("nick");
    	let nick = contenedor.getAttribute("data-nick");
        let idCartel = $(this).attr('data-idCartel');

        $.ajax({
            url: "/borrarCartelesResueltos?idCartel="+idCartel+"&nick="+nick,
            method: "GET",
            success: function(response) {
				contadorCartelesActuales(nick)
				contadorCartelesResueltos(nick)
    			contadorCartelesPendientes(nick)
				mostrarCartelesResueltos(nick)
                mostrarCartelesActuales(nick)
            },
            error: function(error) {
                console.error(error);
                alert("Error al resolver el cartel.");
            }
        });
    });
});

function contadorCartelesActuales(nick) {
    $.ajax({
        url: '/contadorCartelesActuales?nick='+nick,
        method: 'GET',
        success: function(resultText) {
            $("#contadorCartelesActuales").html("("+resultText+")");
        }
    });
}

function contadorCartelesResueltos(nick) {
    $.ajax({
        url: '/contadorCartelesResueltos?nick='+nick,
        method: 'GET',
        success: function(resultText) {
            $("#contadorCartelesResueltos").html("("+resultText+")");
        }
    });
}

function contadorCartelesPendientes(nick) {
    $.ajax({
        url: '/contadorCartelesPendientes?nick='+nick,
        method: 'GET',
        success: function(resultText) {
            $("#contadorCartelesPendientes").html("("+resultText+")");
        }
    });
}

function mostrarCartelesActuales(nick) {
    $.ajax({
        url: '/cartelesActuales?nick='+nick,
        method: 'GET',
        success: function(resultText) {
            $("#cartelesActuales").html(resultText);
        }
    });
}

function mostrarCartelesResueltos(nick) {
    $.ajax({
        url: '/cartelesResueltos?nick='+nick,
        method: 'GET',
        success: function(resultText) {
            $("#cartelesResueltos").html(resultText);
        }
    });
}


function mostrarCartelesPendientes(nick) {
    $.ajax({
        url: '/cartelesPendientes?nick='+nick,
        method: 'GET',
        success: function(resultText) {
            $("#cartelesPendientes").html(resultText);
        }
    });
}

