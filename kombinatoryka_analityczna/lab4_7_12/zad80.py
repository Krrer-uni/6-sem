import matplotlib.pyplot as plt
import numpy as np

from matplotlib import cm
from matplotlib.ticker import LinearLocator

# Make data.
dom_X = np.linspace(-4.0,4.0,1000)
dom_Y = np.linspace(-4.0,4.0,1000)
dom_X, dom_Y = np.meshgrid(dom_X, dom_Y)

x = dom_X
y = dom_Y
# z = np.absolute((dom_X + 1j * dom_Y))
# z = np.absolute((dom_X + 1j * dom_Y)**2)
# z = np.absolute(1/(2 - np.exp(dom_X + 1j * dom_Y)))
# z = np.absolute(1/((1 - (dom_X + 1j * dom_Y))*(1 - (dom_X + 1j * dom_Y)/2)*(1 - (dom_X + 1j * dom_Y)/3)))
# z = np.absolute((1 + (dom_X + 1j * dom_Y) + (dom_X + 1j * dom_Y)**2)/(1 - (dom_X + 1j * dom_Y) - (dom_X + 1j * dom_Y)**2 - (dom_X + 1j * dom_Y)**3))
# z = np.absolute(np.exp(-(dom_X + 1j * dom_Y))/(2 - np.exp(dom_X + 1j * dom_Y)))
z = np.absolute(np.sqrt(1 - 4* (dom_X + 1j * dom_Y)))


plot = plt.figure(figsize=(10, 8))
ax = plot.add_subplot(111, projection='3d')
surf = ax.plot_surface(x, y, z, cmap='viridis')

ax.set_xlabel('Re(e^(it))')
ax.set_ylabel('Im(e^(it))')
ax.set_zlabel('Re(sqrt(r) * exp(it/2))')
ax.set_title('Point 1')
plot.colorbar(surf, shrink=0.5, aspect=5)
plt.show()