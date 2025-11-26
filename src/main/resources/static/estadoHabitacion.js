document.addEventListener("DOMContentLoaded", () => {

    const btn = document.getElementById("btnBuscar");
    const desde = document.getElementById("fechaDesde");
    const hasta = document.getElementById("fechaHasta");

    const errorDesde = document.getElementById("errorDesde");
    const errorHasta = document.getElementById("errorHasta");

    btn.addEventListener("click", () => {

        let valido = true;
        errorDesde.textContent = "";
        errorHasta.textContent = "";

        if (!desde.value) {
            errorDesde.textContent = "Debe ingresar una fecha.";
            valido = false;
        }

        if (!hasta.value) {
            errorHasta.textContent = "Debe ingresar una fecha.";
            valido = false;
        }

        if (valido && hasta.value < desde.value) {
            errorHasta.textContent = "La fecha hasta no puede ser menor que la fecha desde.";
            valido = false;
        }

        if (!valido) return;

        // Llamar al controlador
        window.location.href =
            `/estadoHabitacion?desde=${desde.value}&hasta=${hasta.value}`;
    });

});
