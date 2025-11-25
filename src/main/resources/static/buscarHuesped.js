document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    
    form.addEventListener("submit", function(event) {
        event.preventDefault();
        const url = "/huesped/buscar?buscar=true"
        const apellido = document.getElementById("apellido").value;
        const nombre = document.getElementById("nombre").value;
        const tipo = document.getElementById("tipoDoc").value;
        const dni = document.getElementById("nroDoc").value;
        const params = new URLSearchParams({ apellido, nombre, tipo, dni });
        // fetch(`${url}?${params.toString()}`, {
        //     method: "GET",
        //     headers: {
        //         "Content-Type": "application/json"
        //     }
        // })
        // .then(response => response.text())
        // .then(data => {
        //     //console.log(data);
        //     resultsTable.innerHTML ='';
        //     if (!data) {
        //       resultsTable.innerHTML = '<p>No se encontraron huéspedes que coincidan con los criterios de búsqueda.</p>';  
        //         return;
        //     }
        //     const listaHuespedes = document.createElement("ul");
        //     listaHuespedes.classList.add("list-group");
        //     data = JSON.parse(data);
        //     data.map(huesped => {
        //         const row = document.createElement("li");
        //         row.classList.add("row", "py-2", "border-bottom");
        //         row.innerHTML = `
        //             <div class="col-3 text-truncate">${huesped.apellido}</div>
        //             <div class="col-3 text-truncate">${huesped.nombre}</div>
        //             <div class="col-3 text-truncate">${huesped.tipoDocumento}</div>
        //             <div class="col-3 text-truncate">${huesped.numeroDocumento}</div>
        //         `;
        //         listaHuespedes.appendChild(row);
        //     });
        //     resultsTable.appendChild(listaHuespedes);

        // })
        // .catch(error => console.error('Error:', error));

        // alert("Búsqueda realizada");
        window.location.href = `${url}&${params.toString()}`;
    });

});