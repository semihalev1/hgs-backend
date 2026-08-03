const tryFormatter = new Intl.NumberFormat("tr-TR", {
  style: "currency",
  currency: "TRY",
  minimumFractionDigits: 2,
  maximumFractionDigits: 2,
});

const compactTryFormatter = new Intl.NumberFormat("tr-TR", {
  style: "currency",
  currency: "TRY",
  notation: "compact",
  maximumFractionDigits: 1,
});

const countFormatter = new Intl.NumberFormat("tr-TR");

export const formatCurrency = (value: number): string => {
  return tryFormatter.format(value);
};

export const formatCompactCurrency = (value: number): string => {
  return compactTryFormatter.format(value);
};

export const formatCount = (value: number): string => {
  return countFormatter.format(value);
};
