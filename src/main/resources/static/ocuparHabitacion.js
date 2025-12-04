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
  const modalOcuparIgual = document.getElementById("modal-confirmacion-ocupa");
  const aviso = document.getElementById("aviso");
  const botonOcuparIgual = document.getElementById("btn-ocupar-igual");
  const botonVolver = document.getElementById("btn-volver");
  const modalAviso = document.getElementById("aviso");
  const bsModalAviso = new bootstrap.Modal(modalAviso);
  
  let inputOcultoValor = ''
  let fechaInicioSeleccionada = '';
  let fechaFinSeleccionada = '';

  //const botonReservar = document.getElementById("boton-reservar");
  let contador = 0;
  let seleccionados = [];
  //let arrayReservas = [];
  //let datosReservas = [];
  
  // modalAviso.addEventListener('hidden.bs.modal', function () {
  //   const reserva = [inputOcultoValor, fechaInicioSeleccionada, fechaFinSeleccionada];
  //   console.log("fechas:" + fechaInicioSeleccionada + " - " + fechaFinSeleccionada);
  // });
  
  document.addEventListener('keydown', function (event) {
    const modalVisible = modalAviso.classList.contains('show');
    if (modalVisible) {
      event.preventDefault();
      fetch(`/huesped/buscarOcupantes?preguntar=false&numeroHabitacion=${inputOcultoValor}&fechaInicio=${toISODate(fechaInicioSeleccionada)}&fechaFin=${toISODate(fechaFinSeleccionada)}`);
      window.location.href = `/huesped/buscarOcupantes?preguntar=false&numeroHabitacion=${inputOcultoValor}&fechaInicio=${toISODate(fechaInicioSeleccionada)}&fechaFin=${toISODate(fechaFinSeleccionada)}`;
      //bsModalAviso.hide();
      // const botonCerrar = modalAviso.querySelector('[data-bs-dismiss="modal"]');
      // if (botonCerrar) {
      //   botonCerrar.click();
      // }
    }
  });

  const tabla = document.getElementById("tabla-estados");
  if (tabla) {
    tabla.addEventListener("click", function (event) {
      console.log("Click en la tabla");
      console.log("Contador: " + contador);
      const tdSeleccionado = event.target.closest("td");
      if (tdSeleccionado) {
        seleccionados.push(tdSeleccionado);
        //const inputOculto = tdSeleccionado.querySelector('input[type="hidden"]');
        const trPadre = tdSeleccionado.parentElement;
        const firstTd = trPadre.firstElementChild;
        if (firstTd === tdSeleccionado) {
          return;
        }
        tdSeleccionado.classList.add("seleccion-ocupa");
        contador++;
        if (contador == 2) {
          let seleccionado1 = seleccionados[0];
          let seleccionado2 = seleccionados[1];
          console.log(seleccionado1);
          console.log(seleccionado2);
          const inputOculto1 = seleccionado1.querySelector('input[type="hidden"]');
          const inputOculto2 = seleccionado2.querySelector('input[type="hidden"]');
          inputOcultoValor = inputOculto1
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
            let permiteSeleccionar = true;
            let reservadas = false;
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
              if (!celda.classList.contains("LIBRE") && !celda.classList.contains("RESERVADA")) {
                permiteSeleccionar = false;
                alert("Todas las fechas seleccionadas deben estar disponibles o reservadas.");
                contador = 0;
                seleccionado1.classList.remove("seleccion-ocupa");
                seleccionado2.classList.remove("seleccion-ocupa");
                seleccionados = [];
                break;
              }
              if (celda.classList.contains("RESERVADA")) {
                reservadas = true;
                permiteSeleccionar = false;
                break;
              }
            }
            if (reservadas) {
              // MOSTRAR MODAL CON PREGUNTA
              let reservaInfo = '';
              async function fetchReservaInfo() {
                const fechaInicial = fechasDesordenadas ? firstTd2.textContent.trim('') : firstTd1.textContent.trim('');
                const fechaFinal = fechasDesordenadas ? firstTd1.textContent.trim('') : firstTd2.textContent.trim('');
                const response = await fetch(`/habitaciones/infoReserva?numeroHabitacion=${inputOculto1.value}&fechaInicio=${toISODate(fechaInicial)}&fechaFin=${toISODate(fechaFinal)}`);
                if (response.ok) {
                  const data = await response.json();
                  return data;
                } else {
                  console.error('Error al obtener la información de la reserva');
                  return null;
                }
              }
              fetchReservaInfo().then(data => {
                if (data && data.length > 0) {
                  data.forEach(reserva => {
                    reservaInfo = `La habitación está reservada por ${reserva.nombre} ${reserva.apellido} desde el ${reserva.fechaInicio} hasta el ${reserva.fechaFin}. ¿Desea ocuparla igual?`;
                    const modalBody = aviso.querySelector(".modal-body");
                    modalBody.textContent = reservaInfo;
                  });
                }
              });
              const bsModalOcuparIgual = new bootstrap.Modal(modalOcuparIgual);
              bsModalOcuparIgual.show();
              botonOcuparIgual.addEventListener("click", function () {
                bsModalOcuparIgual.hide();
                permiteSeleccionar = true;
              });
              botonVolver.addEventListener("click", function () {
                contador = 0;
                seleccionado1.classList.remove("seleccion-ocupa");
                seleccionado2.classList.remove("seleccion-ocupa");
                seleccionados = [];
                bsModalOcuparIgual.hide();
              });
            }
            if (permiteSeleccionar) {
              for (let i = ++inicio; i < fin; i++) {
                const celda = filas[i].cells[indiceColumna];
                celda.classList.add("seleccion-ocupa");
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
              fechaInicioSeleccionada = fechaInicial
              fechaFinSeleccionada = fechaFinal
              inputOcultoValor = inputOculto1.value;
              bsModalAviso.show();
              //datosReservas.push(reserva);  // CARGAR DATOS DE PERSONA PARA LA RESERVA, HACER FOR Y CREAR LA LISTA DE OBJETOS
              // console.log("Datos de reservas:", datosReservas);
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

  const modalCliente = document.getElementById("modal-cliente");

});
