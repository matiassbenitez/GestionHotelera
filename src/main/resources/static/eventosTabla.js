document.addEventListener("DOMContentLoaded", function () {
  const botonReservar = document.getElementById("boton-reservar");
  let contador = 0;
  let seleccionados = [];
  let arrayReservas = [];
  let datosReservas = [];
   
  const tabla = document.getElementById("tabla-estados");
  if (tabla) {
    tabla.addEventListener("click", function (event) {
      console.log("Click en la tabla");
      console.log("Contador: " + contador);
      const tdSeleccionado = event.target.closest("td");
      if (tdSeleccionado) {
        seleccionados.push(tdSeleccionado);
        const inputOculto = tdSeleccionado.querySelector('input[type="hidden"]');
        const trPadre = tdSeleccionado.parentElement;
        const firstTd = trPadre.firstElementChild;
        if (firstTd === tdSeleccionado) {
          return;
        }
        tdSeleccionado.classList.add("seleccion-reserva");
        contador++;
        if (contador == 2) {
          let seleccionado1 = seleccionados[0];
          let seleccionado2 = seleccionados[1];
          console.log(seleccionado1);
          console.log(seleccionado2);
          const inputOculto1 = seleccionado1.querySelector('input[type="hidden"]');
          const inputOculto2 = seleccionado2.querySelector('input[type="hidden"]');
          if (inputOculto1.value != inputOculto2.value) { // Habitaciones diferentes
            contador = 0;
            seleccionado1.classList.remove("seleccion-reserva");
            seleccionado2.classList.remove("seleccion-reserva");
            seleccionados = [];
            alert("No puede seleccionar dos habitaciones diferentes.");
          } else { //Habitaciones iguales
            const indiceColumna = seleccionado1.cellIndex;
            const tabla = seleccionado1.closest("table");
            const filas = tabla.rows;
            let todasDisponibles = true;
            const firstTd1 = seleccionado1.parentElement.firstElementChild;
            const firstTd2 = seleccionado2.parentElement.firstElementChild;
            const fecha1 = new Date(firstTd1.textContent.trim(''));
            const fecha2 = new Date(firstTd2.textContent.trim(''));
            let fechasDesordenadas = fecha1 > fecha2
            if (fechasDesordenadas) {
              console.log("Intercambiando s1 y s2:", seleccionado1, seleccionado2);
              [seleccionado1, seleccionado2] = [seleccionado2, seleccionado1];
              console.log("Intercambiados s1 y s2:", seleccionado1, seleccionado2);
            }

            let inicio = Math.min(seleccionado1.parentElement.rowIndex, seleccionado2.parentElement.rowIndex);
            let fin = Math.max(seleccionado1.parentElement.rowIndex, seleccionado2.parentElement.rowIndex);
            console.log("Rango de filas:", inicio, "a", fin);
          
            for (let i = inicio; i <= fin; i++) {
              const celda = filas[i].cells[indiceColumna];
              if (!celda.classList.contains("LIBRE")) {
                todasDisponibles = false;
                alert("Todas las fechas seleccionadas deben estar disponibles.");
                contador = 0;
                seleccionado1.classList.remove("seleccion-reserva");
                seleccionado2.classList.remove("seleccion-reserva");
                seleccionados = [];
                break;
              }
            }
            if (todasDisponibles) {
              for (let i = ++inicio; i < fin; i++) {
                const celda = filas[i].cells[indiceColumna];
                celda.classList.add("seleccion-reserva");
              }
              let fechaInicial = ''
              let fechaFinal = ''
              if (fechasDesordenadas) {
                fechaInicial = firstTd2.textContent.trim('');
                fechaFinal = firstTd1.textContent.trim('');
              } else {
                fechaInicial = firstTd1.textContent.trim('');
                fechaFinal = firstTd2.textContent.trim('');
              }
              const reserva = [inputOculto1.value, fechaInicial, fechaFinal];
              datosReservas.push(reserva);  // CARGAR DATOS DE PERSONA PARA LA RESERVA, HACER FOR Y CREAR LA LISTA DE OBJETOS
              
              seleccionados = [];
              contador = 0;
            }
          }
        }
      }    
    });
  }
  if (botonReservar) {
    botonReservar.addEventListener("click", function () {
        const modalResumen = document.getElementById("modal"); // Tu modal actual (Resumen)
        const modalBodyResumen = document.getElementById("modalReservasBody");
        const modalFooterResumen = document.getElementById("modalReservasFooter");
        
        // Modal del Cliente
        const modalCliente = document.getElementById("modal-cliente");

        if (datosReservas.length === 0) {
            alert("No ha seleccionado ninguna reserva.");
            return;
        }

        // --- 1. LLENAR MODAL DE RESUMEN ---
        modalBodyResumen.innerHTML = '';
        modalFooterResumen.innerHTML = '';
        
        // Llenar el cuerpo con los detalles de las reservas
        datosReservas.forEach(function (reserva, index) {
            const [habitacionId, fechaInicio, fechaFin] = reserva;
            const reservaDiv = document.createElement("div");
            
            reservaDiv.classList.add("p-2", "border-start", "border-3", "border-info", "mb-2", "bg-light");
            
            reservaDiv.innerHTML = `
                <p class="mb-0"><strong>Reserva ${index + 1}:</strong> Habitación Nro ${habitacionId}</p>
                <small class="text-muted">Fechas: ${fechaInicio} al ${fechaFin}</small>
            `;
            
            modalBodyResumen.appendChild(reservaDiv);
        });
        
        // --- 2. CONFIGURAR FOOTER DEL MODAL DE RESUMEN ---
        
        // Botón de CERRAR (Cancelar la acción)
        const botonCerrar = document.createElement("button");
        botonCerrar.textContent = "Cancelar";
        botonCerrar.classList.add("btn", "btn-secondary");
        botonCerrar.setAttribute("data-bs-dismiss", "modal"); 

        // Botón de CONFIRMAR (Avanzar al siguiente modal)
        const botonConfirmar = document.createElement("button");
        botonConfirmar.textContent = "Confirmar y Continuar ➡️";
        botonConfirmar.classList.add("btn", "btn-primary");
        botonConfirmar.type = "button"; // No es un submit
        
        // AÑADIR EL EVENTO PARA MOSTRAR EL SEGUNDO MODAL
        botonConfirmar.addEventListener("click", function () {
            // Ocultar el modal de resumen
            const bsModalResumen = bootstrap.Modal.getInstance(modalResumen);
            if (bsModalResumen) {
                bsModalResumen.hide();
            } else {
                new bootstrap.Modal(modalResumen).hide();
            }
            
            // Mostrar el modal del cliente
            const bsModalCliente = new bootstrap.Modal(modalCliente);
            bsModalCliente.show();
        });

        modalFooterResumen.appendChild(botonCerrar);
        modalFooterResumen.appendChild(botonConfirmar);
        
        // --- 3. MOSTRAR EL PRIMER MODAL (RESUMEN) ---
        const bsModalResumen = new bootstrap.Modal(modalResumen);
        bsModalResumen.show();
    });
}
});