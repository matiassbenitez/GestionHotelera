document.addEventListener('DOMContentLoaded', function() {
    console.log('Index.js loaded');
    const boton = document.getElementById('boton');
    boton.addEventListener('click', function() {
      const div = document.getElementById('falta-nombre');
      div.hidden = !div.hidden;
      div.classList.add('animated', 'fadeIn');
    });
  });