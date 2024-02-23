$(document).ready(function() {
	
	//Variable de busqueda, al principio no se esta buscando nada
	let terminoBusqueda = null;
	
	//Al principio carga todos los perfiles de usuarios
	cargarPerfiles()
	
	//Funcion para cargar los usuarios
	function cargarPerfiles() {
        $.ajax({
            url: '/masPerfiles',
            method: 'GET',
            data: {
                terminoBusqueda: terminoBusqueda
            },
            success: function(data) {
                //limpia la lista de perfiles antes de añadir los nuevos
                $('#listaPerfiles').empty();

                //añade los nuevos usuarios a la lista
                $('#listaPerfiles').html(data);
  
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    }
    	//Buscador de usuario
    	$('#buscador').keyup(function() {
		//si ha escrito algo en la busqueda, entonces la controladora ya sabe que esta usando el buscador
        terminoBusqueda = $(this).val();
        cargarPerfiles();
    });
	
})


    