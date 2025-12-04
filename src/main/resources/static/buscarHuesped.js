let currentSortColumn = null;
let currentSortOrder = 'asc';

document.addEventListener("DOMContentLoaded", function() {
   const urlDiv = document.getElementById("url");
    const form = document.querySelector("form");
    const encabezados = document.querySelectorAll(".fila-encabezados div");
    const urlParams = new URLSearchParams(window.location.search);
    console.log("Encabezados encontrados:", encabezados);
    
    form.addEventListener("submit", function(event) {
        event.preventDefault();
        let url = '';
        if (urlDiv.textContent === 'buscarOcupantes') {
          
          console.log("Buscar OCUPANTES");
          url = `/huesped/${urlDiv.textContent}?numeroHabitacion=${urlParams.get('numeroHabitacion')}&fechaInicio=${urlParams.get('fechaInicio')}&fechaFin=${urlParams.get('fechaFin')}&buscar=true`
        } else {
          console.log("Buscar HUESPEDES");
          url = `/huesped/${urlDiv.textContent}?buscar=true`
        }
        const apellido = document.getElementById("apellido").value;
        const nombre = document.getElementById("nombre").value;
        const tipo = document.getElementById("tipoDoc").value;
        const dni = document.getElementById("nroDoc").value;
        const params = new URLSearchParams({ apellido, nombre, tipo, dni });
        window.location.href = `${url}&${params.toString()}`;
    });

    encabezados.forEach((encabezado, index) => {
      encabezado.style.cursor = "pointer";
      encabezado.dataset.columnIndex = index;  
      encabezado.addEventListener("click", function() {
          const columnIndex = parseInt(encabezado.dataset.columnIndex);
          sortTableByColumn(columnIndex);
      });
    });

    function sortTableByColumn(columnIndex) {
        const listado = document.getElementById("listado-huespedes");
        const ul = listado.querySelector("ul");
        const filas = Array.from(listado.querySelectorAll("ul li"));
        if (filas.length === 0) return;
        let nextSortOrder = 'asc';

        if (currentSortColumn === columnIndex) {
            nextSortOrder = currentSortOrder === 'asc' ? 'desc' : 'asc';
        }
        filas.sort((a, b) => {
            const celdaA = a.children[columnIndex].textContent.trim().toLowerCase();
            const celdaB = b.children[columnIndex].textContent.trim().toLowerCase();
            if (celdaA < celdaB) return nextSortOrder === 'asc' ? -1 : 1;
            if (celdaA > celdaB) return nextSortOrder === 'asc' ? 1 : -1;
            return 0;
        });

        filas.forEach(fila => ul.appendChild(fila));
        currentSortColumn = columnIndex;
        currentSortOrder = nextSortOrder;

        document.querySelectorAll(".sort-icon").forEach(icon => icon.textContent = '');

        const sortIndicator = nextSortOrder === 'asc' ? ' ↑' : ' ↓';
        console.log("Setting sort indicator:", sortIndicator);
        console.log("For header:", encabezados[currentSortColumn]);
        encabezados[currentSortColumn].querySelector(".sort-icon").textContent = sortIndicator;
    }

});