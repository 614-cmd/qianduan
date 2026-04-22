let requireComponent = import.meta.globEager('./packages/**/*.vue')

let components = {}
Object.keys(requireComponent).forEach(fileName => {
  const cmp = requireComponent[fileName].default
  components[cmp.name] = cmp
})
export default components