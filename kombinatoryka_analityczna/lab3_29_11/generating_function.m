generating_plot(100)
%limit_lab(100);
%complex_plot();
function generating_plot(order)
    syms x;
    
    % Define the function
    f = 1/(1 - x)^x;
    
    % Use the taylor function to compute the Taylor series
    taylor_series = taylor(f, x, 'Order', order + 1);
    
    % Extract coefficients from the Taylor series
    coefficients = coeffs(taylor_series, x, 'All');
    
    figure;
    plot(0:order, double(flip(coefficients)), 'LineWidth', 2);
    xlabel('Coefficient Index');
    ylabel('Coefficient Value');
    title('Coefficients of Taylor Series Expansion');
    grid on;
end


function limit_lab(b)
    syms k n;
    
    x_values = linspace(1, b,b);
    A = symsum(1/(factorial(k))^2,0,n);
    B = subs(A,n,x_values);
    figure;
    plot(x_values, B, 'LineWidth', 2);
    hold on;
    xlabel('x');
    ylabel('y');
    title('Taylor Series Expansion of 1/(1-x)^x');
    legend('show');
    grid on;
end

function complex_plot()
   [x,y] = meshgrid(-3 : 0.05: 3);
    s = x + 1j*y;
    z=abs((5^2)./(s.^2 + 2*0.4*5.*s + 5^2));
    figure;
    surf(x, y, z)
end

