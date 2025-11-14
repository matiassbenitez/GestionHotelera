getPaises();

async function getPaises() {
  await fetch('https://restcountries.com/v3/subregion/south%20america')
    .then(response => response.json())
    .then(data => {
      const paisSelect = document.getElementById('pais');
      const telefonoSelect = document.getElementById('prefijo');
      data.sort((a, b) => a.name.common.localeCompare(b.name.common));
      data.forEach(country => {
        const optionTel = document.createElement('option');
        optionTel.value = country.idd.root + (country.idd.suffixes ? country.idd.suffixes[0] : '');
        optionTel.textContent = `${country.name.common} (${optionTel.value})`;
        telefonoSelect.appendChild(optionTel);
        const option = document.createElement('option');
        option.value = country.name.common;
        option.textContent = country.name.common;
        paisSelect.appendChild(option);
      });
    })
    .catch(error => console.error('Error al cargar los países:', error));
}