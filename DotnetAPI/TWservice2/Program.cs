using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Cors;
using TWservice2.Models;
using Steeltoe.Discovery.Client;

namespace TWservice2
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add Steeltoe Discovery Client
            builder.Services.AddDiscoveryClient(builder.Configuration);

            // Add services to the container
            builder.Services.AddControllers()
                .AddJsonOptions(x =>
                    x.JsonSerializerOptions.ReferenceHandler = System.Text.Json.Serialization.ReferenceHandler.IgnoreCycles);

            // ✅ Use MySQL instead of SQL Server
            var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");
            builder.Services.AddDbContext<p10_tiffinwalaContext>(options =>
                options.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString)));

            // ✅ Configure CORS
            //builder.Services.AddCors(options =>
            //{
            //    options.AddPolicy("AllowAll",
            //        builder => builder.AllowAnyOrigin()
            //                          .AllowAnyMethod()
            //                          .AllowAnyHeader());
            //});

            // ✅ Swagger
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            var app = builder.Build();

            // Configure the HTTP request pipeline
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            //app.UseHttpsRedirection();
            app.UseAuthorization();

            app.UseDiscoveryClient();


            // Apply CORS policy
            app.UseCors("AllowAll");

            app.MapControllers();
            app.Run();
        }
    }
}
