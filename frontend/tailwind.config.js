/** @type {import('tailwindcss').Config} */
export default {
  content: [
    './src/pages/**/*.{jsx,js}',
    './src/components/**/*.{jsx,js}'
  ],
  theme: {
    screens: {
      sm: '480px',
      md: '768px',
      lg: '976px',
      xl: '1440px',
    },
    fontFamily: {
      sans: ['Graphik', 'sans-serif'],
      serif: ['Merriweather', 'serif'],
    },
  },
  plugins: [],
}

