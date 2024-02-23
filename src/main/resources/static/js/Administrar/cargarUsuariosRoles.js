$(document).ready(function() {
	//variable de busqueda, al principio no se esta buscando nada
	let terminoBusquedaUsuario = null;
	let terminoBusquedaRoles = null;
	
	//carga las subvistar de usuario y roles
	cargarUsuarios()
	cargarRoles()
	
	//Al inicializar la paguina: el boton de enviar comentario esta en gris y desactivado. hasta que se escriba algo
	$('#btnCrearUsuario').prop('disabled', true).css('background-color', 'gray');
	
	//Funcion para cargar los usuarios
	function cargarUsuarios() {
        $.ajax({
            url: '/masUsuarios',
            method: 'GET',
            data: {
                terminoBusquedaUsuario: terminoBusquedaUsuario
            },
            success: function(data) {
                //limpia la lista de usuarios antes de añadir los nuevos
                $('#listaUsuarios').empty();

                //añade los nuevos usuarios a la lista
                $('#listaUsuarios').html(data);
               
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    }
    
    //Funcion para cargar de roles
    function cargarRoles() {
        $.ajax({
            url: '/masRoles',
            method: 'GET',
            data: {
                terminoBusquedaRoles: terminoBusquedaRoles
            },
            success: function(data) {
                //limpia la lista de roles antes de añadir los nuevos
                $('#listaRoles').empty();

                //añade los nuevos roles a la lista
                $('#listaRoles').html(data);
               
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    }
    
    //Buscador de usuario
   	$('#buscadorUsuarios').keyup(function() {
		//si ha escrito algo en la busqueda, entonces la controladora ya sabe que esta usando el buscador
        terminoBusquedaUsuario = $(this).val();
        cargarUsuarios();
    });
    
    //Buscador de roles
    $('#buscadorRoles').keyup(function() {
		//si ha escrito algo en la busqueda, entonces la controladora ya sabe que esta usando el buscador
        terminoBusquedaRoles = $(this).val();
        cargarRoles();
    });
    
    //Boton para borrar usuarios
     $('body').on('click','#btnEliminarUsuario', function() {
        
        //la ID del cartel se saca de la array de usuario
		//Al pulsar el boton respectivo del cartel se saca la ID de ese usuario
        let idUsuario = $(this).attr('data-idUsuario');
        
        $.ajax({
            url: '/borrarUsuarios?idUsuario='+idUsuario,
            method: 'GET',
            success: function(data) {
				//Recarga todos los usuarios
          		cargarUsuarios();
               
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    });
    
    //Boton para crear un rol
    $('body').on('click','#btnCrearRol', function() {
		
		//recoge el dato que esta dentro del input, para ponerle nombre al nuevo rol
        let data_crearRol = $('#nombreRol').val();
        
        $.ajax({
            url: '/crearRol?nombreRol='+data_crearRol,
            method: 'GET',
            success: function(data) {
				$('#btnCrearUsuario').prop('disabled', true).css('background-color', 'gray');
				$('#nombreRol').val('')
				//recarga todos los roles
          		cargarRoles();
               
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    });
    
    //Boton para borrar roles
    $('body').on('click','#btnBorrarRol', function() {
		
		//la ID del cartel se saca de la array de rol
		//Al pulsar el boton respectivo del cartel se saca la ID de ese rol
       	let idRol = $(this).attr('data-idRol');
       	
        $.ajax({
            url: '/borrarRol?idRol='+idRol,
            method: 'GET',
            success: function(data) {
				//Recarga los usuarios y roles
				cargarUsuarios()
				cargarRoles()
               
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    });
	
	//Evento de boton de crear roles
	// Cambia el color y desactiva o activa del boton enviarComentario, dependiendo si hay algo escrito en el input del comentario
	$('#nombreRol').on('input', function() {
	    let nombreRol = $(this).val().trim();
	    if (nombreRol === "") {
	        $('#btnCrearUsuario').prop('disabled', true).css('background-color', 'gray');
	    } else {
	        $('#btnCrearUsuario').prop('disabled', false).css('background-color', ''); 
	    }
	});
	
})