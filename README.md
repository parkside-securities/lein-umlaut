# lein-umlaut

CLI for umlaut.

## Usage

Put `[lein-umlaut "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your `project.clj`.

Usage examples:
```
lein umlaut graphql in.umlaut in2.umlaut out.graphql
lein umlaut dot in.umlaut in2.umlaut all.png
lein umlaut lacinia in.umlaut in2.umlaut lacinia.edn
```

## Help?
`lein help umlaut`
