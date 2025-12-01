/**
 * Quick and dirty way to get the correct url (when debugging locally vs through Spring).
 */
export function getUrl() {
  return document.location.host === "localhost:4200" ? "http://localhost:8080/" : document.location.href;
}
