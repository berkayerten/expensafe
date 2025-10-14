import { createApp } from 'vue';
import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura';
import App from './App.vue';
import router from './router';

import Button from 'primevue/button';
import Card from 'primevue/card';
import Chart from 'primevue/chart';
import DatePicker from 'primevue/datepicker';
import Select from 'primevue/select';
import Menu from 'primevue/menu';
import Panel from 'primevue/panel';
import Dialog from 'primevue/dialog';
import InputGroupAddon from 'primevue/inputgroupaddon';
import InputGroup from 'primevue/inputgroup';
import InputNumber from 'primevue/inputnumber';
import InputText from 'primevue/inputtext';
import Checkbox from 'primevue/checkbox';
import Message from 'primevue/message';
import SpeedDial from 'primevue/speeddial';
import Popover from 'primevue/popover';
import IftaLabel from 'primevue/iftalabel';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';
import 'chart.js/auto';

const app = createApp(App);

app.use(PrimeVue, {
  theme: {
    preset: Aura,
    options: {
      prefix: 'p',
      darkModeSelector: 'false',
      cssLayer: false
    }
  },
});
app.component('UILibButton', Button);
app.component('UILibCard', Card);
app.component('UILibChart', Chart);
app.component('UILibDatePicker', DatePicker);
app.component('UILibSelect', Select);
app.component('UILibMenu', Menu);
app.component('UILibPanel', Panel);
app.component('UILibDialog', Dialog);
app.component('UILibInputGroupAddon', InputGroupAddon);
app.component('UILibInputGroup', InputGroup);
app.component('UILibInputNumber', InputNumber);
app.component('UILibInputText', InputText);
app.component('UILibCheckbox', Checkbox);
app.component('UILibMessage', Message);
app.component('UILibSpeedDial', SpeedDial);
app.component('UILibPopover', Popover);
app.component('UILibIftaLabel', IftaLabel);

app.use(router);
app.mount('#app');
