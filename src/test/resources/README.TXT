files in "src/main/resources/**" will be deployed into "target/classes/**".

to access a resource file
  "src/main/resources/my_resource_file"
in code, use:
  URL url = this.getClass().getResource("/my_resource_file");
