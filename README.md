# Comparte Mesa

## Motivación

Actualmente RENFE ofrece una tarifa que resulta muy económica a la hora de viajar en AVE. Esta tarifa permite comprar un billete de una mesa en las que pueden ir cuatro personas en lugar de forma individual. El precio por persona es del 60% comparado con el billete individuale estándar.

Para usar esta tarifa y que resulte económica es necesario ser cuatro personas, aquí es donde entra en juego nuestra aplicación. Usando esta app se puede encontrar los compañeros que falten y así poder comprar billete para mesa, ahorrando dinero.

La aplicación tratará de emparejar al usuario formando grupos de cuatro, una vez formado el grupo establecerá un chat que permitirá la comunicación para poder ponerse de acuerdo y comprar el billete.

## Resumen

La Aplicación, a rasgos generales, lo que hace es dar al usuario la posibilidad de encontrar, si existiera, un grupo de tres personas más que compartan el origen, el destino y el horario de su viaje para así poder aprovechar de la tarifa de mesa.

### Estado de la aplicación

La aplicación se encuentra en fase beta. Por ahora las funcionalidades disponibles son:

- Crear mesa seleccionando origen, destino y tiempo de salida del tren.
- Listar las mesas creadas por otros usarios y unirse a una de ellas.
- Eliminar y salirse de las mesas.
- Configurar un nombre de usuario.
- Servidor AXIS2 para servir los datos de las mesas a los clientes.
- Clientes con librerías KSOAP2 para consumir el servicio web proporcionado por AXIS2.

Funcionalidades a implementar en el futuro:

- Migrar el servidor a Node.JS.
- Usar GCM para notificaciones PUSH.
- Servidor XMPP para el chat multiusuario.
- Login mediante cuenta de Google+, Facebook o Twitter.
- Compartir enlace a mesas por redes sociales.
- Perfiles de usuario con valoraciones.
- Opción de aumentar la visibilidad de una mesa mediante micropagos.

## Estructura de la aplicación

Ahora pasamos a describir como hemos desarrollado la aplicación explicando los aspectos más destacados de el código de la misma.

### Packet: data

Aquí se encuentran todos objetos que almacenan información. Tenemos los siguientes:

- `Cities.java`: Se usa para mapear enteros a ciudades. Cada entero representa una ciudad y este objeto se encarga de convertir cada entero a su correspondiente String. En el futuro será capaz de conocer a qué ciudad destino se puede llegar desde qué ciudad origen. Por ahora simplemente está implementando el mapeo.
- `User.java`: Almacena información de usuarios. Por ejemplo el nombre, el email o la contraseña. Por ahora sólo está implementado el nombre. Además tiene un campo que es un ID único por cada usuario.
- `Table.java`: Representa una mesa. Tiene un ID único, un origen, un destino, una hora de salida y una lista de hasta cuatro usuario. El primero usuario es el que la creó y el único que puede borrarla, los demás usuario sólo pueden abandonarla.

### Packet: tables

Este paquete contiene lo referente a las mesas.

- `TableFragment.java`: Es el fragmento que muesetra toda la información de la mesa a la que estamos asociado. Si no hay ninguna mesa a la que estemos asociado, nos mostrará un botón para crear una mesa nueva. Entre las cosas que muestra nos encontramos el origen y el destino, la lista de los usuarios que están unidos actualmente a la mesa y un chat para la comunicación (acualmente no implementado).
- `AddTableActivity.java`: Actividad encargada de mostrar la interfaz para crear una nueva mesa. Nos permite elegir el origen, el destino y una hora de salida.

### Packet: search

- `SearchFragment`: Este framento nos muestra las mesas que han sido creadas en el servidor. Se muestra el origen y el destino y podemos unirnos a la que más nos convenga. En el futuro implementará un filtro por origen y destino. También podría ser interesante que hubiese algunas mesas "patrocinadas" que apareciesen en posiciones más altas si el usuario ha efectuado un pago.

### Packet: interfaces

Este paquete contine interfaces usadas por nuestro programa.

- `TableOperationCallback`: Esta interfaz se usa para funciones de callbacks en tareas asíncronas que realizan conexiones contra el servidor.

### Packet communication

Este paquete contiene objetos que heredan de la clase `AsyncTask`, que ejecutan una tarea en un hilo independiente y, al finalizar ejecutan un método. Hemos aprovechado este método para llamar a una función de callback usando la interfaz `TableOperationCallback`. De esta nuestro programa funciona de forma totalmente asíncrona sin tiempos de espera en el hilo de la interfaz gráfica. El propósito de todas estos objetos es la comunicación con el servidor por medio de KSOAP2.

- `ChangeNameTask`: Se usa para cambiar el nombre de usuario.
- `CreateTableTask`: Crea una mesa en el servidor.
- `CreateUserTask`: Crea un usuario en el servidor.
- `JoinTableTask`: Se usa para unirse a una mesa ya existente en el servidor.
- `LoadMyTableTask`: Se usa para preguntar al servidor si estamos en alguna mesa y, en caso afirmativo, descargarnos los datos de tal mesa.
- `LoadTablesTask`: Descarga una lista de las mesas que se encuentran en el servidor.
- `RemoveMyTableTask`: Dejamos una mesa a la que pertenecemos. En caso de ser el dueño de esta mesa el servidor la eliminará.

### ComparteMesaApplication.java

Es un objeto de clase aplicación. Su cometido es ofrecer "variables globales" y métodos estáticos para almacenar objetos a los que necesitaremos acceder desde diferentes partes de la aplicación.

### ConfigActivity.java

Es una actividad que simplemente nos permite cambiar nuestro nombre de usuario por ahora.

### MainActivity.java

Es la actividad principal de la aplicación y la más importante. vamos a detallar sus métodos.

#### OnCreate()

Se carga el layout en pantalla. Después se cargan las preferencias y se llama a la función `init()` que inicia la interfaz de la aplicación.

```java
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        // Cargo las preferencias
        loadPrefs();

        // Inicializo el NavigationDrawer y la ActionBar
        init();

    }
```

### loadPrefs()

Usamos un objejto `SharedPreferences` para almacenar nuestro ID de usuario. Si no tenemos ninguno le pedirmemos al servidor que nos de de alta y nos proporciene uno.

Puede verse como hemos usado una función de callback con una tarea asíncrona para comunicarnos con el servidor. Usamos la misma filosofía durante toda la aplicación.

```java
private void loadPrefs() {
        int mode = Activity.MODE_PRIVATE;
        final SharedPreferences pref = getSharedPreferences("prefs", mode);
        String myUUID = pref.getString("myUUID", "null");
        String myName = pref.getString("myName", "Usuario");

        Log.d("PREFS", "Cargada UUID: " + myUUID);

        // Cargo el UUID almacenado, si no hay ninguno pido uno nuevo al servidor

        if (myUUID.contentEquals("null")) {
            CreateUser createUser = new CreateUser(new TableOperationCallback() {
                @Override
                public void onTaskDone(Object... params) {
                    String myName = pref.getString("myName", "User");

                    ChangeNameTask changeNameTask = new ChangeNameTask();
                    changeNameTask.execute(myName);
                }
            });
            createUser.execute(myName);
        } else {
            ComparteMesaApplication.setMyUUID(myUUID);
            Log.d("MAIN", "Iniciada aplicación con UUID: " + ComparteMesaApplication.getMyUUID());
        }
    }
```

#### DrawerNavigation

Hemos usado un DrawerNavigation por lo que necesitamos algunos métodos para iniciarlo correctamente:

```java
	/**
     * Función de callback para el NavigationDrawer y la ActionBar
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
```

```java
    /**
     * Función de callback para el NavigationDrawer y la ActionBar
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
```

```java
    /**
     * Inicializo el NavigatonDrawer y la ActionBar
     */
    private void init() {
            final String[] opcionesMenu = getResources().getStringArray(R.array.navigation_drawer_elements);
            final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            final ListView drawerList = (ListView) findViewById(R.id.left_drawer);
    
            drawerList.setAdapter(new ArrayAdapter<String>(
                    getSupportActionBar().getThemedContext(),
                    android.R.layout.simple_list_item_1, opcionesMenu));
    
            drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view,
                                        int position, long id) {
    
                    switch (position) {
                        case 0:
                            loadFragmentTable();
                            break;
                        case 1:
                            loadSearch();
                            break;
                        case 2:
                            configActivity();
                            break;
                    }
    
                    drawerList.setItemChecked(position, true);
                    drawerLayout.closeDrawer(drawerList);
                }
    
            });
    
            drawerToggle = new ActionBarDrawerToggle(this,
                    drawerLayout,
                    R.drawable.ic_navigation_drawer,
                    R.string.drawer_open,
                    R.string.drawer_close) {
    
                public void onDrawerClosed(View view) {
                    getSupportActionBar().setTitle(tituloSeccion);
                    ActivityCompat.invalidateOptionsMenu(MainActivity.this);
                }
    
                public void onDrawerOpened(View drawerView) {
                    getSupportActionBar().setTitle(getTitle());
                    ActivityCompat.invalidateOptionsMenu(MainActivity.this);
                }
            };
    
            drawerLayout.setDrawerListener(drawerToggle);
    
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
```

```java
    /**
     * Función de callback para el NavigationDrawer y la ActionBar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_add:
                intent = new Intent(this, AddTableActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_discard:
                deleteTable();
                onResume();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
```

```java
	/**
     * Función de callback para el botón añadir cuando no hay mesas
     */
    public void onClick(View view) {
        if (view.getId() == R.id.create_table) {
            Intent intent = new Intent(this, AddTableActivity.class);
            startActivity(intent);
        }
    }
```
#### configActivity()

Aquí llamamos a la actividad `ConfigActivity`.

```java
	public void configActivity() {
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }
```

#### loadSearch()

Cargamos las mesas en el servidor y llamamos al fragmento `SearchFragment` para que nos las muestre en una lista.

```java
    private void loadSearch() {

        LoadTablesTask loadTables = new LoadTablesTask(new TableOperationCallback() {
            @Override
            public void onTaskDone(Object... loadedTables) {


                tables = (List<Table>) loadedTables[0];

                FragmentManager fragmentManager =
                        getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new SearchFragment())
                        .commit();

                tituloSeccion = "Lista de mesas";
                getSupportActionBar().setTitle(tituloSeccion);
                setSupportProgressBarIndeterminateVisibility(false);
            }
        });
        setSupportProgressBarIndeterminateVisibility(true);
        loadTables.execute();
    }
```

#### loadFragmentTable()

Descargamos del servidor los datos de la mesa a la que pertenecemos, en caso de pertenecer a alguna, y tras cargar los datos mostramos el fragmento para que nos los muestre.

```java
	    private void loadFragmentTable() {
            setSupportProgressBarIndeterminateVisibility(true);
    
            LoadMyTableTask loadMyTableTask = new LoadMyTableTask(new TableOperationCallback() {
    
                @Override
                public void onTaskDone(Object... object) {
    
                    Table myTable = (Table) object[0];
    
                    if (myTable != null)
                        ComparteMesaApplication.setMyTable(myTable);
    
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, new TableFragment())
                            .commit();
    
                    tituloSeccion = getTitle();
                    getSupportActionBar().setTitle(tituloSeccion);
                    setSupportProgressBarIndeterminateVisibility(false);
    
                }
            });
            loadMyTableTask.execute();
        }
```

#### onResume() y onPause()

Usamos `onPause()` para guardar las preferencias y `onResume` para recargar el fragmento principal `TableFragment`.

```java
	@Override
    public void onResume() {
        super.onResume();

        loadFragmentTable();
    }
```

```java
    @Override
    public void onPause() {
        super.onPause();

        // Guardo las preferencias en los estados de pausa

        String myUUID = ComparteMesaApplication.getMyUUID();

        if (myUUID != null) {
            int mode = Activity.MODE_PRIVATE;
            SharedPreferences pref = getSharedPreferences("prefs", mode);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("myUUID", myUUID);
            editor.commit();
            Log.d("PREFS", "Guardada UUID: " + myUUID);
        }
    }
```

#### getTables()

Disponemos de una función que usará el fragmento `SearchFragment` para obtener las mesas una vez recibidas del servidor.

```java
   public List<Table> getTables() {
        return tables;
    }
```

## Conclusión

Se puede decir que la aplicación aún tiene mucho que avanzar, ya que ahora mismo no es funcional. Sin embargo, ha sido un muy buen comienzo para aprender las bases de programación de Android y en un futuro cercano llegar a mejorarla lo suficiente como para poder subirla a Google Play.
