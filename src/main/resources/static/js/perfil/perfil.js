$(document).ready(function(){
	//La variable del nick que le pertenece al perfil
	let elementoH1 = document.getElementById("nick");
    let nick = elementoH1.getAttribute("data-nick");
    //Contadores de Carteles
    contadorCartelesActuales(nick)
    contadorCartelesResueltos(nick)
    contadorCartelesPendientes(nick)
    //Subvistas de Carteles
    mostrarCartelesActuales(nick);
    mostrarCartelesResueltos(nick)
    mostrarCartelesPendientes(nick)
});

//Boton resuelto (cambia de no-resuelto a resuelto)
$(document).ready(function() {
    $('body').on('click','#botonResuelto', function() {
		let elementoH1 = document.getElementById("nick");
    	let nick = elementoH1.getAttribute("data-nick");
    	
    	//la ID del cartel se saca de la array de carteles
		//Al pulsar el boton respectivo del cartel se saca la ID de ese cartel	
        let idCartel = $(this).attr('data-idCartel');

        $.ajax({
            url: "/resolverCartel?idCartel="+idCartel,
            method: "GET",
            success: function(response) {
			   //Se actualizan todos los contadores
			   contadorCartelesActuales(nick)
			   contadorCartelesResueltos(nick)
    		   contadorCartelesPendientes(nick)
    		   
    		   //Actualizan las subvistas de las listas de carteles
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

//Boton no resuelto (cambia de resuelto a no-resuelto)
$(document).ready(function() {
    $('body').on('click','#botonNoResuelto', function() {
		let elementoH1 = document.getElementById("nick");
    	let nick = elementoH1.getAttribute("data-nick");
    	
    	//la ID del cartel se saca de la array de carteles
		//Al pulsar el boton respectivo del cartel se saca la ID de ese cartel	
        let idCartel = $(this).attr('data-idCartel');

        $.ajax({
            url: "/noResolverCartel?idCartel="+idCartel,
            method: "GET",
            success: function(response) {
				//Se actualizan todos los contadores
				contadorCartelesActuales(nick)
				contadorCartelesResueltos(nick)
    			contadorCartelesPendientes(nick)
    			
    			//Actualizan las subvistas de las listas de carteles
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

//boton de borrar de carteles actuales
$(document).ready(function() {
    $('body').on('click','#botonBorrarActuales', function() {
		let elementoH1 = document.getElementById("nick");
    	let nick = elementoH1.getAttribute("data-nick");
    	
    	//la ID del cartel se saca de la array de carteles
		//Al pulsar el boton respectivo del cartel se saca la ID de ese cartel	
        let idCartel = $(this).attr('data-idCartel');

        $.ajax({
            url: "/borrarCartelesActuales?idCartel="+idCartel+"&nick="+nick,
            method: "GET",
            success: function(response) {
				//Se actualizan todos los contadores
				contadorCartelesActuales(nick)
				contadorCartelesResueltos(nick)
    			contadorCartelesPendientes(nick)
    			
    			//Actualizan las subvistas de las listas de carteles
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

//boton de borrar de carteles resueltos
$(document).ready(function() {
    $('body').on('click','#botonBorrarResueltos', function() {
		let elementoH1 = document.getElementById("nick");
    	let nick = elementoH1.getAttribute("data-nick");
    	
    	//la ID del cartel se saca de la array de carteles
		//Al pulsar el boton respectivo del cartel se saca la ID de ese cartel	
        let idCartel = $(this).attr('data-idCartel');

        $.ajax({
            url: "/borrarCartelesResueltos?idCartel="+idCartel+"&nick="+nick,
            method: "GET",
            success: function(response) {
				//Se actualizan todos los contadores
				contadorCartelesActuales(nick)
				contadorCartelesResueltos(nick)
    			contadorCartelesPendientes(nick)
    			
    			//Actualizan las subvistas de las listas de carteles
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

//Funcion que devuelve el numero de publicaciones de carteles actuales sin resolver asociadas al perfil
function contadorCartelesActuales(nick) {
    $.ajax({
        url: '/contadorCartelesActuales?nick='+nick,
        method: 'GET',
        success: function(resultText) {
            $("#contadorCartelesActuales").html("("+resultText+")");
        }
    });
}

//Funcion que devuelve el numero de publicaciones de carteles resueltos asociadas al perfil
function contadorCartelesResueltos(nick) {
    $.ajax({
        url: '/contadorCartelesResueltos?nick='+nick,
        method: 'GET',
        success: function(resultText) {
            $("#contadorCartelesResueltos").html("("+resultText+")");
        }
    });
}

//Funcion que devuelve el numero de publicaciones de carteles pendientes asociadas al perfil
function contadorCartelesPendientes(nick) {
    $.ajax({
        url: '/contadorCartelesPendientes?nick='+nick,
        method: 'GET',
        success: function(resultText) {
            $("#contadorCartelesPendientes").html("("+resultText+")");
        }
    });
}

//Muestra los carteles del perdil que tiene actuales sin resolver (PerfilController | controladora subvista: cartelesActuales)
function mostrarCartelesActuales(nick) {
    $.ajax({
        url: '/cartelesActuales?nick='+nick,
        method: 'GET',
        success: function(resultText) {
            $("#cartelesActuales").html(resultText);
        }
    });
}

//Muestra los carteles del perfil que tiene resueltos (PerfilController | controladora subvista: cartelesResueltos)
function mostrarCartelesResueltos(nick) {
    $.ajax({
        url: '/cartelesResueltos?nick='+nick,
        method: 'GET',
        success: function(resultText) {
            $("#cartelesResueltos").html(resultText);
        }
    });
}

//Muestra los carteles del perfil que tiene pendientes para espera de ser aprobados (PerfilController | controladora subvista: cartelesPendientes)
function mostrarCartelesPendientes(nick) {
    $.ajax({
        url: '/cartelesPendientes?nick='+nick,
        method: 'GET',
        success: function(resultText) {
            $("#cartelesPendientes").html(resultText);
        }
    });
}

