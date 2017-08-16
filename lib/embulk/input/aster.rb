Embulk::JavaPlugin.register_input(
  "aster", "org.embulk.input.aster.AsterInputPlugin",
  File.expand_path('../../../../classpath', __FILE__))
