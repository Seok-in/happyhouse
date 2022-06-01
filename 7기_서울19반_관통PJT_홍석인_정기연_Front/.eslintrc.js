module.exports = {
  root: true,

  env: {
    node: true,
  },

  plugins: ["vuetify"],

  extends: [
    "vuetify",
    "plugin:vue/essential",
    "eslint:recommended",
    "plugin:prettier/recommended",
  ],

  parserOptions: {
    parser: "@babel/eslint-parser",
  },

  rules: {
    // https://github.com/babel/babel-eslint/issues/681#issuecomment-420663038
    "template-curly-spacing": "off",
    indent: "off",
    //
    "no-unused-vars": "warn",
    "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off",
    "prettier/prettier": [
      "error",
      {
        singleQuote: false,
        semi: true,
        tabWidth: 2,
        // trailingComma: "all",
        printWidth: 80,
        bracketSpacing: true,
        arrowParens: "always",
        endOfLine: "auto",
        useTabs: false,
      },
    ],
  },

  overrides: [
    {
      files: [
        "**/__tests__/*.{j,t}s?(x)",
        "**/tests/unit/**/*.spec.{j,t}s?(x)",
      ],
      env: {
        jest: true,
      },
    },
  ],
};
