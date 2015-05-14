package net.genesishub.gFeatures;

import net.genesishub.gFeatures.Feature.Base.Configure;

public class Setup {
	Configs config;
	public void onSetup() {
		Configure.onSetup();
		net.genesishub.gFeatures.Feature.CTF.Configure.onSetup();
		net.genesishub.gFeatures.Feature.gDestroyCritical.Configure.onSetup();
		net.genesishub.gFeatures.Feature.GenesisAccess.Configure.onSetup();
		/*Package[] packs = Package.getPackages();
		Bukkit.getLogger().info("Start2");
		for(Package pac : packs){
			if(pac.getName().startsWith("net.genesishub.gFeatures")){
				Bukkit.getLogger().info("Start");
				Class[] classes;
				try {
					classes = getClasses(pac.getName());
				
				for(Class classd : classes){
					Bukkit.getLogger().info(classd.getName());
				for (Method method : classd.getMethods())
				{
					Bukkit.getLogger().info(method.getName());
				    if (method.isAnnotationPresent(Configs.class))
				    {
				    	try {
							method.invoke(null, null);
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
				}
				}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				Bukkit.getLogger().info(pac.getName());
				Annotation[] ann = pac.getAnnotations();
				for(Annotation annt : ann){
					Bukkit.getLogger().info(annt.toString());
					if(annt.equals(Configs.class)){
						Method[] method = annt.getClass().getMethods();
						for(Method meth : method){
							Annotation[] annts = meth.getAnnotations();
							Bukkit.getLogger().info(meth.getName());
								for(Annotation ants : annts){
									Bukkit.getLogger().info(ants.toString());
									if(ants.equals(Configs.class)){
										try {
											meth.invoke(null, null);
										} catch (IllegalAccessException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IllegalArgumentException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (InvocationTargetException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
						}
					}
				}
			}
		}
			/*try {
				Class[] classes = getClasses("net.genesishub.gFeatures.Feature.Base");
			for(Class classs : classes){
				Method[] methods = classs.getClass().getMethods();
				Bukkit.getLogger().info(classs.getName());
			 	for (Method method : methods) {
				 Configs annos = method.getAnnotation(Configs.class);
				 Bukkit.getLogger().info(method.getName());
            			if (annos != null) {
                			try {
                				method.invoke(classs);
                				Bukkit.getLogger().info("FOUND!");
                			} catch (Exception e) {
                   				e.printStackTrace();
                			}
            			}
        			}
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}*/
	}
	/*@SuppressWarnings("rawtypes")
	private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}
	@SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
		}*/
}