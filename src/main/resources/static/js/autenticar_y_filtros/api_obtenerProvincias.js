let selectProvincias = $("#zonaGeografica");

// Utiliza Overpass API para obtener las provincias de España
$.ajax({
    url: "https://overpass-api.de/api/interpreter?data=[out:json];area['ISO3166-1'='ES'][admin_level=2]->.boundaryarea;(rel(area.boundaryarea)['admin_level'='6'];);out;",
    dataType: "json",
    success: function(data) {
    // Verifica si existen datos y si hay una propiedad 'tags' con 'name'
        if (Array.isArray(data.elements) && data.elements.length > 0 && data.elements[0].tags && data.elements[0].tags.name) {
            let provincias = data.elements.map(function(element) {
            return element.tags.name;
            });

            // Si las hay añadamelos en las opciones del select
            for (let i = 0; i < provincias.length; i++) {
                let provincia = provincias[i];
                let option = $("<option>").text(provincia);
                selectProvincias.append(option);
            }
        }else {
            console.log("Ha surgido un error con la API:", data);
        }

    },
    error: function(error) {
        console.log("Error al obtener provincias:", error);
    }
});