function toISODate(dateString) {
  if (!dateString) return null;
  // Asume el formato DD/MM/YYYY, separa por '/'
  const parts = dateString.split('/');
  if (parts.length === 3) {
    // Devuelve YYYY-MM-DD
    return `${parts[2]}-${parts[1]}-${parts[0]}`;
  }
  // Si ya está en otro formato (como YYYY-MM-DD), lo devuelve tal cual
  return dateString;
}

function parseDate(dateString) {
    if (!dateString) return null;
    
    // Asume formato DD/MM/YYYY
    const parts = dateString.split('/');
    
    // Crea un objeto Date usando YYYY, MM-1 (meses son 0-indexados) y DD
    // parts[2] = YYYY, parts[1] = MM, parts[0] = DD
    return new Date(parts[2], parts[1] - 1, parts[0]); 
}

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
            const fecha1String = firstTd1.textContent.trim('');
            const fecha2String = firstTd2.textContent.trim('');
            const fecha1 = parseDate(fecha1String);
            const fecha2 = parseDate(fecha2String);
            console.log("Fecha 1:", fecha1);
            console.log("Fecha 2:", fecha2);
            let fechasDesordenadas = (fecha1.getTime() > fecha2.getTime());
            console.log("Fechas desordenadas:", fechasDesordenadas);
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
              console.log("Datos de reservas:", datosReservas);
              seleccionados = [];
              contador = 0;
            }
          }
        }
      }
    });
  }
  const modalResumen = document.getElementById("modal"); // Tu modal actual (Resumen)
  const modalBodyResumen = document.getElementById("modalReservasBody");
  const modalFooterResumen = document.getElementById("modalReservasFooter");
  const formulario = document.getElementById("form-confirmar-reserva");

  formulario.addEventListener("submit", function (e) {
    e.preventDefault(); // Prevenir el envío por defecto
    const formData = new FormData(e.target);
    const clienteDatos = {};
    console.log("Datos del formulario de cliente:" , Array.from(formData.entries()));
    for (const [key, value] of formData.entries()) {
      // 'key' será el atributo 'name' del input (ej: 'nombre', 'apellido')
      // 'value' será el texto que el usuario escribió
      clienteDatos[key] = value;
      console.log(`Clave: ${key}, Valor: ${value}`);
    }

    datosReservas.forEach((reserva) => {
      console.log("feacha a iso:", toISODate(reserva[1]));
      const reservaCompleta = {
        numeroHabitacion: reserva[0],
        fechaInicio: toISODate(reserva[1]),
        fechaFin: toISODate(reserva[2]),
        nombre: clienteDatos['nombre'],
        apellido: clienteDatos['apellido'],
        telefono: clienteDatos['telefono']
      };
      arrayReservas.push(reservaCompleta);
    });
    console.log("Reservas a enviar al servidor:", arrayReservas);
    enviarReservasAlServidor(arrayReservas);
  });

  // Modal del Cliente
  const modalCliente = document.getElementById("modal-cliente");

  if (botonReservar) {
    botonReservar.addEventListener("click", function () {

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

function enviarReservasAlServidor(arrayReservas) {
  const reservasJSON = JSON.stringify(arrayReservas);
  fetch('/habitaciones/reservar', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: reservasJSON
  })
    .then(response => {
      if (response.ok) {
        return response.json();
      } else {

       return response.text().then(text => {
                // Si el texto es HTML, aquí lo capturaremos.
                console.error("Respuesta de error no JSON del servidor:", text);
                
                // Lanzar un error para ir al bloque .catch
                throw new Error(`Error ${response.status}: El servidor devolvió una página de error (HTML).`);
            });
      }
    })
    .then(data => {
      console.log('Reservas confirmadas:', data);
      // Aquí puedes manejar la respuesta del servidor, como mostrar un mensaje de éxito
      alert("Reservas confirmadas con éxito.");
      window.location.href = "/";
    })
    .catch(error => {
      console.error('Error al confirmar las reservas:', error);
      alert("Hubo un error al confirmar las reservas. Por favor, inténtelo de nuevo.");
    });
}